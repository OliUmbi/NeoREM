package ch.oliumbi.neorem.mappers;

import ch.oliumbi.neorem.entities.Event;
import ch.oliumbi.neorem.ingest.Dicom;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class EventMapper {

    // todo split up
    // todo create instance object
    // todo add support for image files
    // todo search for secondary values or cases where information needs to be extracted elsewhere
    // todo maybe restructure the value retrieval because it is kinda big

    public List<Event> mapIrradiationEvent(Dicom dicom) {
        List<Event> events = new ArrayList<>();

        List<Dicom> irradiationEvents = dicom
                .first("ContentSequence")
                .map(d -> d.all("Irradiation Event X-Ray Data"))
                .orElse(Collections.emptyList());

        if (irradiationEvents.isEmpty()) {
            return events;
        }

        for (Dicom irradiationEvent : irradiationEvents) {
            Event event = new Event();

            event.setExternalId(irradiationEvent
                    .first("Irradiation Event UID")
                    .flatMap(Dicom::string)
                    .orElse(null));

            event.setModality(irradiationEvent
                    .first("Irradiation Event Type")
                    .flatMap(Dicom::modality)
                    .orElse(null));

            event.setComment(irradiationEvent
                    .first("Comment")
                    .flatMap(Dicom::string)
                    .orElse(null));

            event.setDate(irradiationEvent
                    .first("Date Time Started")
                    .flatMap(Dicom::localDateTime)
                    .map(LocalDateTime::toLocalDate)
                    .orElse(null));

            if (event.getDate() == null) {
                event.setDate(irradiationEvent
                        .first("DateTime Started")
                        .flatMap(Dicom::localDateTime)
                        .map(LocalDateTime::toLocalDate)
                        .orElse(null));
            }

            event.setTime(irradiationEvent
                    .first("Date Time Started")
                    .flatMap(Dicom::localDateTime)
                    .map(LocalDateTime::toLocalTime)
                    .orElse(null));

            if (event.getTime() == null) {
                event.setTime(irradiationEvent
                        .first("DateTime Started")
                        .flatMap(Dicom::localDateTime)
                        .map(LocalDateTime::toLocalTime)
                        .orElse(null));
            }

            event.setProtocol(irradiationEvent
                    .first("Acquisition Protocol")
                    .flatMap(Dicom::string)
                    .orElse(null));

            event.setType(irradiationEvent
                    .first("Irradiation Event Type")
                    .flatMap(Dicom::string)
                    .orElse(null));

            event.setPlane(irradiationEvent
                    .first("Acquisition Plane")
                    .flatMap(Dicom::string)
                    .orElse(null));

            event.setRegion(irradiationEvent
                    .first("Target Region")
                    .flatMap(Dicom::string)
                    .orElse(null));

            event.setAnatomy(irradiationEvent
                    .first("Anatomical structure")
                    .flatMap(Dicom::string)
                    .orElse(null));

            event.setLaterality(irradiationEvent
                    .first("Anatomical structure")
                    .flatMap(d -> d.first("Laterality"))
                    .flatMap(Dicom::laterality)
                    .orElse(null));

            event.setView(irradiationEvent
                    .first("Image View")
                    .flatMap(Dicom::string)
                    .orElse(null));

            event.setOrientation(irradiationEvent
                    .first("Patient Orientation")
                    .flatMap(Dicom::string)
                    .orElse(null));

            event.setExposureTime(irradiationEvent
                    .first("Exposure Time")
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            event.setDuration(irradiationEvent
                    .first("Irradiation Duration")
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            event.setPulseRate(irradiationEvent
                    .first("Pulse Rate")
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            event.setPulseTime(irradiationEvent
                    .medianFloatingPoint("Pulse Width")
                    .orElse(null));

            event.setVoltagePeak(irradiationEvent
                    .medianFloatingPoint("KVP")
                    .orElse(null));

            event.setTubeCurrent(irradiationEvent
                    .medianFloatingPoint("X-Ray Tube Current")
                    .orElse(null));

            if (event.getTubeCurrent() == null) {
                event.setTubeCurrent(irradiationEvent
                        .first("Average X-Ray Tube Current")
                        .flatMap(Dicom::floatingPoint)
                        .orElse(null));
            }
            // todo not sure
            if (event.getTubeCurrent() == null) {
                event.setTubeCurrent(irradiationEvent
                        .first("mA")
                        .flatMap(Dicom::floatingPoint)
                        .orElse(null));
            }

            // todo mammography merge information with mean and report peaks where possible like tubeCurrentPeak
            // todo mammography maybe report all filters used or just the dominant one because there wont be any else in 99.9% cases or is irrelevant
            // todo pulse width is pulse time (ms) used for mammo

            event.setTubeCurrentTime(irradiationEvent
                    .medianFloatingPoint("Exposure")
                    .orElse(null));

            event.setDoseAreaProduct(irradiationEvent
                    .first("Dose Area Product")
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            event.setDoseReferencePoint(irradiationEvent
                    .first("Dose (RP)")
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            // todo not sure
            event.setDoseOrgan(irradiationEvent
                    .first("OrganDose")
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            event.setCompressionThickness(irradiationEvent
                    .first("Compression Thickness")
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            event.setMaterialTarget(irradiationEvent
                    .first("Anode Target Material")
                    .flatMap(Dicom::material)
                    .orElse(null));

            event.setMaterialFilter(irradiationEvent
                    .first("X-Ray Filters")
                    .flatMap(d -> d.first("X-Ray Filter Material"))
                    .flatMap(Dicom::material)
                    .orElse(null));

            events.add(event);
        }

        return events;
    }

    public List<Event> mapComputedTomographyAcquisition(Dicom dicom) {
        List<Event> events = new ArrayList<>();

        List<Dicom> computedTomographyAcquisitions = dicom
                .first("ContentSequence")
                .map(d -> d.all("CT Acquisition"))
                .orElse(Collections.emptyList());

        if (computedTomographyAcquisitions.isEmpty()) {
            return events;
        }

        for (Dicom computedTomographyAcquisition : computedTomographyAcquisitions) {
            Event event = new Event();

            event.setExternalId(computedTomographyAcquisition
                    .first("Irradiation Event UID")
                    .flatMap(Dicom::string)
                    .orElse(null));

            event.setComment(computedTomographyAcquisition
                    .first("Comment")
                    .flatMap(Dicom::string)
                    .orElse(null));

            event.setProtocol(computedTomographyAcquisition
                    .first("Acquisition Protocol")
                    .flatMap(Dicom::string)
                    .orElse(null));

            event.setType(computedTomographyAcquisition
                    .first("CT Acquisition Type")
                    .flatMap(Dicom::string)
                    .orElse(null));

            event.setRegion(computedTomographyAcquisition
                    .first("Target Region")
                    .flatMap(Dicom::string)
                    .orElse(null));

            event.setExposureTime(computedTomographyAcquisition
                    .first("CT Acquisition Parameters")
                    .flatMap(d -> d.first("Exposure Time"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            event.setExposureTimeRotation(computedTomographyAcquisition
                    .first("CT Acquisition Parameters")
                    .flatMap(d -> d.first("CT X-Ray Source Parameters"))
                    .flatMap(d -> d.first("Exposure Time per Rotation"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            event.setScanningLength(computedTomographyAcquisition
                    .first("CT Acquisition Parameters")
                    .flatMap(d -> d.first("Scanning Length"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            event.setVoltagePeak(computedTomographyAcquisition
                    .first("CT Acquisition Parameters")
                    .flatMap(d -> d.first("CT X-Ray Source Parameters"))
                    .flatMap(d -> d.first("KVP"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            event.setTubeCurrent(computedTomographyAcquisition
                    .first("CT Acquisition Parameters")
                    .flatMap(d -> d.first("CT X-Ray Source Parameters"))
                    .flatMap(d -> d.first("X-Ray Tube Current"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            event.setTubeCurrentPeak(computedTomographyAcquisition
                    .first("CT Acquisition Parameters")
                    .flatMap(d -> d.first("CT X-Ray Source Parameters"))
                    .flatMap(d -> d.first("Maximum X-ray Tube Current"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            event.setDoseLengthProduct(computedTomographyAcquisition
                    .first("CT Dose")
                    .flatMap(d -> d.first("DLP"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            event.setDoseIndexVolume(computedTomographyAcquisition
                    .first("CT Dose")
                    .flatMap(d -> d.first("Mean CTDIvol"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            event.setModulation(computedTomographyAcquisition
                    .first("Dose Reduce Parameters")
                    .flatMap(d -> d.first("Modulation"))
                    .flatMap(Dicom::string)
                    .orElse(null));

            event.setModulation(computedTomographyAcquisition
                    .first("X-Ray Modulation Type")
                    .flatMap(Dicom::string)
                    .orElse(null));

            events.add(event);
        }

        return events;
    }

    public List<Event> mapOrganDose(Dicom dicom) {
        List<Event> events = new ArrayList<>();

        List<Dicom> organDoses = dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Radiopharmaceutical Administration"))
                .map(d -> d.all("Organ Dose Information"))
                .orElse(Collections.emptyList());

        if (organDoses.isEmpty()) {
            return events;
        }

        for (Dicom organDose : organDoses) {
            Event event = new Event();

            event.setAnatomy(organDose
                    .first("Finding Site")
                    .flatMap(Dicom::string)
                    .orElse(null));

            event.setLaterality(organDose
                    .first("Laterality")
                    .flatMap(Dicom::laterality)
                    .orElse(null));

            event.setDoseOrgan(organDose
                    .first("Organ Dose")
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            events.add(event);
        }

        return events;
    }
}
