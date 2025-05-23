package ch.oliumbi.neorem.ingest;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.ElementDictionary;
import org.dcm4che3.data.Sequence;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.util.TagUtils;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DicomFile {

    private static final ElementDictionary elementDictionary = ElementDictionary.getStandardElementDictionary();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
    private static final DateTimeFormatter timePrecisionFormatter = DateTimeFormatter.ofPattern("HHmmss.SSSSSS");

    public static void main(String[] args) throws Exception {

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
                prefix += "   ";
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
