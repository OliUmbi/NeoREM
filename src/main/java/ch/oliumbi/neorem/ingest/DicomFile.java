package ch.oliumbi.neorem.ingest;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.ElementDictionary;
import org.dcm4che3.data.Sequence;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.util.TagUtils;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DicomFile {

    private static final ElementDictionary elementDictionary = ElementDictionary.getStandardElementDictionary();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
    private static final DateTimeFormatter timePrecisionFormatter = DateTimeFormatter.ofPattern("HHmmss.SSSSSS");

    public static void main(String[] args) throws Exception {

        URL resource = DicomFile.class.getResource("/orso");

        for (File file : Paths.get(resource.toURI()).toFile().listFiles()) {

            System.out.println("──────── start " + file.getName() + "────────");

            DicomInputStream dicomInputStream = new DicomInputStream(file);
            Attributes attributes = dicomInputStream.readDataset();

            Sequence sequence = attributes.getSequence(Tag.ContentSequence);
            List<DicomStructuredReport> dicomStructuredReports = contentSequence(sequence);

            List<List<Object>> entries = parse(attributes);
            print(entries, 0);

            System.out.println("──────── end " + file.getName() + " ────────");
        }
    }

    public static List<DicomStructuredReport> contentSequence(Sequence sequence) throws Exception {
        if (sequence == null) {
            return null;
        }

        List<DicomStructuredReport> dicomStructuredReports = new ArrayList<>();

        for (Attributes attributes : sequence) {
            DicomStructuredReport dicomStructuredReport = new DicomStructuredReport();
            dicomStructuredReport.setRelationship(attributes.getString(Tag.RelationshipType));
            dicomStructuredReport.setType(attributes.getString(Tag.ValueType));

            Attributes conceptNameCode = attributes.getNestedDataset(Tag.ConceptNameCodeSequence);
            if (conceptNameCode != null) {
                dicomStructuredReport.setName(conceptNameCode.getString(Tag.CodeMeaning));
            }

            switch (dicomStructuredReport.getType()) {
                case "CONTAINER" -> dicomStructuredReport.setValue(attributes.getString(Tag.ContinuityOfContent));
                case "TEXT" -> dicomStructuredReport.setValue(attributes.getString(Tag.TextValue));
                case "PERSON NAME", "PNAME" -> dicomStructuredReport.setValue(attributes.getString(Tag.PersonName));
                case "CODE" -> {
                    Attributes conceptCode = attributes.getNestedDataset(Tag.ConceptCodeSequence);
                    if (conceptCode != null) {
                        dicomStructuredReport.setValue(conceptCode.getString(Tag.CodeMeaning));
                    }
                }
                case "UIDREF" -> dicomStructuredReport.setValue(attributes.getString(Tag.UID));
                case "DATETIME" -> dicomStructuredReport.setValue(attributes.getString(Tag.DateTime));
                case "DATE" -> dicomStructuredReport.setValue(attributes.getString(Tag.Date));
                case "TIME" -> dicomStructuredReport.setValue(attributes.getString(Tag.Time));
                case "NUM" -> {
                        Attributes measuredValue = attributes.getNestedDataset(Tag.MeasuredValueSequence);

                        if (measuredValue != null) {
                            dicomStructuredReport.setValue(measuredValue.getString(Tag.NumericValue));
                            dicomStructuredReport.setFloatingPoint(measuredValue.getString(Tag.FloatingPointValue));
                            dicomStructuredReport.setNumerator(measuredValue.getString(Tag.RationalNumeratorValue));
                            dicomStructuredReport.setDenominator(measuredValue.getString(Tag.RationalDenominatorValue));

                            Attributes measurementUnitsCode = measuredValue.getNestedDataset(Tag.MeasurementUnitsCodeSequence);

                            if (measurementUnitsCode != null) {
                                dicomStructuredReport.setUnit(measurementUnitsCode.getString(Tag.CodeMeaning));
                            }
                        }
                }
                case "IMAGE" -> {
                    Attributes conceptCode = attributes.getNestedDataset(Tag.ReferencedSOPSequence);
                    if (conceptCode != null) {
                        dicomStructuredReport.setValue(conceptCode.getString(Tag.ReferencedSOPInstanceUID));
                    }
                }
                case "COMPOSITE", "WAVEFORM", "SCOORD", "SCOORD3D", "TCOORD", "TABLE" -> {
                    System.out.println("!!! ignored " + dicomStructuredReport.getType() + " | " + attributes);
                }
                default -> {
                    System.out.println("??? invalid " + dicomStructuredReport.getType() + " | " + attributes);
                }
            }

            List<DicomStructuredReport> children = contentSequence(attributes.getSequence(Tag.ContentSequence));
            dicomStructuredReport.setChildren(children);

            dicomStructuredReports.add(dicomStructuredReport);
        }

        return dicomStructuredReports;
    }

    public static List<List<Object>> parse(Attributes attributes) throws Exception {
        List<List<Object>> list = new ArrayList<>();

        for (int tag : attributes.tags()) {

            List<Object> row = new ArrayList<>();
            row.add(TagUtils.toString(tag) + " " + elementDictionary.keywordOf(tag));

            Sequence sequence = attributes.getSequence(tag);

            if (sequence != null && !sequence.isEmpty()) {
                List<Object> children = new ArrayList<>();

                if (tag == Tag.SourceImageSequence
                        || tag == Tag.ContributingSOPInstancesReferenceSequence) {
                    continue;
                } else if (tag == Tag.ConceptCodeSequence
                        || tag == Tag.ConceptNameCodeSequence
                        || tag == Tag.MeasurementUnitsCodeSequence
                        || tag == Tag.ProcedureCodeSequence
                        || tag == Tag.AnatomicRegionSequence
                        || tag == Tag.RequestedProcedureCodeSequence
                        || tag == Tag.PerformedProcedureCodeSequence
                        || tag == Tag.ReasonForPerformedProcedureCodeSequence
                        || tag == Tag.ViewCodeSequence) {
                    for (Attributes child : sequence) {
                        for (int conceptCode : child.tags()) {
                            if (conceptCode == Tag.CodeMeaning) {
                                row.add(child.getString(conceptCode));
                                list.add(row);
                            }
                        }
                    }
                } else {
                    for (Attributes child : sequence) {
                        children.addAll(parse(child));
                    }

                    row.add(children);
                    list.add(row);
                }
            } else {
                row.add(attributes.getString(tag));
                list.add(row);
            }
        }

        return list;
    }

    public static void print(List<List<Object>> entries, int level) {
        for (List<Object> entry : entries) {
            Object key = entry.get(0);
            Object value = entry.get(1);

            String prefix = "";
            for (int i = 0; i < level; i++) {
                prefix += "│  ";
            }

            if (value instanceof List<?> children) {
                System.out.println(prefix + "├─ " + key);
                print((List<List<Object>>) children, level + 1);
            } else {
                System.out.println(prefix + "├─ " + key + " -> " + value);
            }
        }
    }
}
