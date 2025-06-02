package ch.oliumbi.neorem.ingest;

import ch.oliumbi.neorem.entities.Device;
import ch.oliumbi.neorem.entities.Patient;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.ElementDictionary;
import org.dcm4che3.data.Sequence;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.util.TagUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
public class Ingest {

    private static final ElementDictionary ELEMENT_DICTIONARY = ElementDictionary.getStandardElementDictionary();

    private final PatientMapper patientMapper;
    private final DeviceMapper deviceMapper;

    public Ingest(PatientMapper patientMapper, DeviceMapper deviceMapper) {
        this.patientMapper = patientMapper;
        this.deviceMapper = deviceMapper;
    }

    /*
    todo

    - get file
    - parse from dicom to internal model with usable datatypes
    - determine strategy
      - functionally would be nice
    - create database representation from internal model
    - save to database conditionally (probably merge old and new)

    update task information
    error handling
    think about hl7
    */

    public static void main(String[] args) throws Exception {
        Ingest ingest = new Ingest(new PatientMapper(), new DeviceMapper());
        ingest.ingest(new File("C:\\Users\\ksaolumb\\projects\\NeoREM\\src\\main\\resources\\mg\\MG-Im-Hologic-PropProj.dcm"));
    }

    public void ingest(File file) {

        if (!file.exists()) {
            // todo handle file does not exist
            throw new RuntimeException();
        }

        try (DicomInputStream dicomInputStream = new DicomInputStream(file)) {
            Attributes attributes = dicomInputStream.readDataset();

            Dicom dicom = parse(attributes);

            System.out.println(dicom);

            Patient patient = patientMapper.map(new Patient(), dicom);
            Device device = deviceMapper.map(new Device(), dicom);


        } catch (IOException e) {
            // todo handle input stream exception / reading attributes
            throw new RuntimeException(e);
        }
    }

    public Dicom parse(Attributes attributes) {
        Dicom dicom = new Dicom();

        for (int tag : attributes.tags()) {
            Dicom child = new Dicom();

            String name = ELEMENT_DICTIONARY.keywordOf(tag);
            if (name != null && !name.isBlank()) {
                child.setName(name);
            } else {
                child.setName(TagUtils.toString(tag));
            }

            if (tag == Tag.ContentSequence) {
                Sequence sequence = attributes.getSequence(Tag.ContentSequence);
                child.addAll(parseStructuredReport(sequence));
            } else if (attributes.getValue(tag) instanceof Sequence sequence) {
                for (Attributes attributes1 : sequence) {
                    child.add(parse(attributes1));
                }
            } else {
                child.setValue(attributes.getString(tag));
            }

            dicom.add(child);
        }

        return dicom;
    }

    private List<Dicom> parseStructuredReport(Sequence sequence) {
        if (sequence == null) {
            return Collections.emptyList();
        }

        List<Dicom> dicoms = new ArrayList<>();

        for (Attributes attributes : sequence) {
            Dicom dicom = new Dicom();

            String type = attributes.getString(Tag.ValueType);

            Attributes conceptNameCode = attributes.getNestedDataset(Tag.ConceptNameCodeSequence);
            if (conceptNameCode != null) {
                dicom.setName(conceptNameCode.getString(Tag.CodeMeaning));
            }

            switch (type) {
                case "CONTAINER" -> dicom.setValue(attributes.getString(Tag.ContinuityOfContent));
                case "TEXT" -> dicom.setValue(attributes.getString(Tag.TextValue));
                case "PERSON NAME", "PNAME" -> dicom.setValue(attributes.getString(Tag.PersonName));
                case "CODE" -> {
                    Attributes conceptCode = attributes.getNestedDataset(Tag.ConceptCodeSequence);
                    if (conceptCode != null) {
                        dicom.setValue(conceptCode.getString(Tag.CodeMeaning));
                    }
                }
                case "UIDREF" -> dicom.setValue(attributes.getString(Tag.UID));
                case "DATETIME" -> dicom.setValue(attributes.getString(Tag.DateTime));
                case "DATE" -> dicom.setValue(attributes.getString(Tag.Date));
                case "TIME" -> dicom.setValue(attributes.getString(Tag.Time));
                case "NUM" -> {
                    Attributes measuredValue = attributes.getNestedDataset(Tag.MeasuredValueSequence);
                    if (measuredValue != null) {
                        dicom.setValue(measuredValue.getString(Tag.NumericValue));
                    }
                }
                case "IMAGE" -> {
                    Attributes conceptCode = attributes.getNestedDataset(Tag.ReferencedSOPSequence);
                    if (conceptCode != null) {
                        dicom.setValue(conceptCode.getString(Tag.ReferencedSOPInstanceUID));
                    }
                }
                case "COMPOSITE", "WAVEFORM", "SCOORD", "SCOORD3D", "TCOORD", "TABLE" -> {
                    // todo handle
                    System.out.println("!!! ignored " + type + " | " + attributes);
                }
                default -> {
                    // todo handle
                    System.out.println("??? invalid " + type + " | " + attributes);
                }
            }

            dicom.addAll(parseStructuredReport(attributes.getSequence(Tag.ContentSequence)));

            dicoms.add(dicom);
        }

        return dicoms;
    }
}
