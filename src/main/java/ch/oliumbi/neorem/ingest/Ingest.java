package ch.oliumbi.neorem.ingest;

import ch.oliumbi.neorem.entities.Device;
import ch.oliumbi.neorem.entities.Event;
import ch.oliumbi.neorem.entities.Patient;
import ch.oliumbi.neorem.entities.Study;
import ch.oliumbi.neorem.mappers.DeviceMapper;
import ch.oliumbi.neorem.mappers.EventMapper;
import ch.oliumbi.neorem.mappers.PatientMapper;
import ch.oliumbi.neorem.mappers.StudyMapper;
import ch.oliumbi.neorem.repositories.DeviceRepository;
import ch.oliumbi.neorem.repositories.EventRepository;
import ch.oliumbi.neorem.repositories.PatientRepository;
import ch.oliumbi.neorem.repositories.StudyRepository;
import jakarta.transaction.Transactional;
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
    private final StudyMapper studyMapper;
    private final EventMapper eventMapper;
    private final PatientRepository patientRepository;
    private final DeviceRepository deviceRepository;
    private final StudyRepository studyRepository;
    private final EventRepository eventRepository;

    public Ingest(PatientMapper patientMapper, DeviceMapper deviceMapper, StudyMapper studyMapper, EventMapper eventMapper, PatientRepository patientRepository, DeviceRepository deviceRepository, StudyRepository studyRepository, EventRepository eventRepository) {
        this.patientMapper = patientMapper;
        this.deviceMapper = deviceMapper;
        this.studyMapper = studyMapper;
        this.eventMapper = eventMapper;
        this.patientRepository = patientRepository;
        this.deviceRepository = deviceRepository;
        this.studyRepository = studyRepository;
        this.eventRepository = eventRepository;
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

//    public static void main(String[] args) throws Exception {
//        Ingest ingest = new Ingest(new PatientMapper(), new DeviceMapper(), new StudyMapper(), new InstanceMapper());
//
//        List<String> folders = List.of("/ct", "/fl", "/mg", "/nm", "/rg", "/orso");
//        Map<String, Object> data = new HashMap<>();
//
//        for (String folder : folders) {
//
//            URL resource = DicomFile.class.getResource(folder);
//
//            for (File file : Paths.get(resource.toURI()).toFile().listFiles()) {
//
//                try (DicomInputStream dicomInputStream = new DicomInputStream(file)) {
//                    Attributes attributes = dicomInputStream.readDataset();
//
//                    Dicom dicom = ingest.parse(attributes);
//
//                    resolve(data, dicom);
//                }
//            }
//        }
//        System.out.println(data);
//    }

    public static void resolve(Map<String, Object> data, Dicom dicom) {

        for (Dicom child : dicom) {
            if (child.string() == null && child.isEmpty() || child.name() != null && child.name().startsWith("(")) {
                continue;
            }

            if (!data.containsKey(child.name())) {
                HashMap<String, Object> children = new HashMap<>();

                resolve(children, child);

                data.put(child.name(), children);
            } else {
                resolve((Map<String, Object>) data.get(child.name()), child);
            }
        }
    }

    @Transactional
    public void ingest(File file) {

        if (!file.exists()) {
            // todo handle file does not exist
            throw new RuntimeException();
        }

        try (DicomInputStream dicomInputStream = new DicomInputStream(file)) {
            Attributes attributes = dicomInputStream.readDataset();

            Dicom dicom = parse(attributes);

            Patient patient = patientMapper.map(dicom);
            Device device = deviceMapper.map(dicom);
            Study study = studyMapper.map(dicom);
            List<Event> events = new ArrayList<>();
            events.addAll(eventMapper.mapIrradiationEvent(dicom));
            events.addAll(eventMapper.mapComputedTomographyAcquisition(dicom));
            events.addAll(eventMapper.mapOrganDose(dicom));

            patient.setId(UUID.randomUUID());
            device.setId(UUID.randomUUID());
            study.setId(UUID.randomUUID());
            for (Event event : events) {
                event.setId(UUID.randomUUID());
            }

            if (patient.getExternalId() != null) {
                Optional<Patient> oldPatient = patientRepository.findByExternalId(patient.getExternalId());
                if (oldPatient.isPresent()) {
                    patient = oldPatient.get().merge(patient);
                }
            }

            if (!(device.getManufacturer() == null && device.getModel() == null && device.getSerial() == null && device.getSoftware() == null)) {
                Optional<Device> oldDevice = deviceRepository.findByManufacturerAndModelAndSerialAndSoftware(device.getManufacturer(), device.getModel(), device.getSerial(), device.getSoftware());
                if (oldDevice.isPresent()) {
                    device = oldDevice.get().merge(device);
                }
            }

            if (study.getExternalId() != null) {
                Optional<Study> oldStudy = studyRepository.findByExternalId(study.getExternalId());
                if (oldStudy.isPresent()) {
                    study = oldStudy.get().merge(study);
                }
            }

            for (int i = 0; i < events.size(); i++) {
                if (events.get(i).getExternalId() != null) {
                    Optional<Event> oldEvent = eventRepository.findByExternalId(events.get(i).getExternalId());
                    if (oldEvent.isPresent()) {
                        events.set(i, oldEvent.get().merge(events.get(i)));
                    }
                }
            }

            study.setPatientId(patient.getId());
            study.setDeviceId(device.getId());
            for (Event event : events) {
                event.setStudyId(study.getId());
            }

            patientRepository.save(patient);
            patientRepository.flush();

            deviceRepository.save(device);
            deviceRepository.flush();

            studyRepository.save(study);
            studyRepository.flush();

            eventRepository.saveAll(events);
            eventRepository.flush();

        } catch (IOException e) {
            // todo handle input stream exception / reading attributes
            System.out.println(file.getName());
            e.printStackTrace();
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
