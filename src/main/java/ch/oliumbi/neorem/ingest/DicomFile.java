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

        URL resource = DicomFile.class.getResource("/ct");

        for (File file : Paths.get(resource.toURI()).toFile().listFiles()) {

            System.out.println("──────── start " + file.getName() + "────────");

            DicomInputStream dicomInputStream = new DicomInputStream(file);

            Attributes attributes = dicomInputStream.readDataset();

            Sequence sequence = attributes.getSequence(Tag.ContentSequence);

            List<DicomStructuredReport> dicomStructuredReports = contentSequence(sequence);

            System.out.println(dicomStructuredReports);

            List<List<Object>> entries = parse(attributes);

            print(entries, 0);

            System.out.println("──────── end " + file.getName() + "────────");
        }

        /*
        for (int i = 1; i <= 20; i++) {
            try (InputStream inputStream = DicomFile.class.getResourceAsStream("/test-" + i + ".dcm");
                 DicomInputStream dicomInputStream = new DicomInputStream(inputStream)) {

                Attributes attributes = dicomInputStream.readDataset(-1);

                Patient patient = new Patient();
                patient.setId(UUID.randomUUID());
                patient.setPatientID(attributes.getString(Tag.PatientID));
                patient.setIssuerOfPatientID(attributes.getString(Tag.IssuerOfPatientID));
                patient.setPatientSex(attributes.getString(Tag.PatientSex));

                Study study = new Study();
                study.setPatientAge(attributes.getString(Tag.PatientAge));
                study.setPregnancyStatus(attributes.getInt(Tag.PregnancyStatus, -1)); // todo review getting ints
                study.setStudyInstanceUID(attributes.getString(Tag.StudyInstanceUID));
                study.setAccessionNumber(attributes.getString(Tag.AccessionNumber));
                study.setStudyDate(attributes.getString(Tag.StudyDate));
                study.setStudyTime(attributes.getString(Tag.StudyTime));
                study.setStudyDescription(attributes.getString(Tag.StudyDescription));
                study.setReferringPhysicianName(attributes.getString(Tag.ReferringPhysicianName));
                study.setRequestingPhysician(attributes.getString(Tag.RequestingPhysician));
                study.setInstitutionName(attributes.getString(Tag.InstitutionName));
                study.setInstitutionalDepartmentName(attributes.getString(Tag.InstitutionalDepartmentName));
                study.setStationName(attributes.getString(Tag.StationName));

                Instance instance = new Instance();
                instance.setSopClassUID(attributes.getString(Tag.SOPClassUID));
                instance.setSopInstanceUID(attributes.getString(Tag.SOPInstanceUID));
                instance.setInstanceNumber(attributes.getInt(Tag.InstanceNumber, 0)); // todo review ints
                instance.setContentDate(attributes.getString(Tag.ContentDate));
                instance.setContentTime(attributes.getString(Tag.ContentTime));
                instance.setModality(attributes.getString(Tag.Modality));
                instance.setSeriesDescription(attributes.getString(Tag.SeriesDescription));

                List<List<Object>> entries = parse(attributes);

                print(entries, 0);

                System.out.println("─────────────────────────────────────────────");
                System.out.println("─────────────────────────────────────────────");
                System.out.println("─────────────────────────────────────────────");
            }
        }
         */
    }

    public static List<DicomStructuredReport> contentSequence(Sequence sequence) throws Exception {
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
                case "CODE" -> {
                    Attributes conceptCode = attributes.getNestedDataset(Tag.ConceptCodeSequence);
                    if (conceptCode != null) {
                        dicomStructuredReport.setValue(conceptCode.getString(Tag.CodeMeaning));
                    }
                }
                case "UIDREF" -> dicomStructuredReport.setValue(attributes.getString(Tag.UID));
                case "DATETIME" -> dicomStructuredReport.setValue(attributes.getString(Tag.DateTime));
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
                default -> System.out.println("!!! error " + attributes);
            }


            Sequence children = attributes.getSequence(Tag.ContentSequence);
            if (children != null) {
                dicomStructuredReport.setChildren(contentSequence(children));
            }

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
