package ch.oliumbi.neorem.ingest;

import ch.oliumbi.neorem.entities.Instance;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InstanceMapper {

    // todo split up
    // todo create instance object
    // todo add support for image files
    // todo search for secondary values or cases where information needs to be extracted elsewhere
    // todo maybe restructure the value retrieval because it is kinda big
    public Instance map(Instance instance, Dicom dicom) {

        // todo support multiple instances
        // todo support ct and avery variation

        instance.setEventId(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("Irradiation Event UID"))
                .map(Dicom::string)
                .orElse(null));

        if (instance.getEventId() == null) {
            instance.setEventId(dicom
                    .first("ContentSequence")
                    .flatMap(d -> d.first("CT Acquisition"))
                    .flatMap(d -> d.first("Irradiation Event UID"))
                    .map(Dicom::string)
                    .orElse(null));
        }

        instance.setModality(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("Irradiation Event Type"))
                .flatMap(Dicom::modality)
                .orElse(null));

        instance.setComment(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("Comment"))
                .map(Dicom::string)
                .orElse(null));

        if (instance.getComment() == null) {
            instance.setComment(dicom
                    .first("ContentSequence")
                    .flatMap(d -> d.first("CT Acquisition"))
                    .flatMap(d -> d.first("Comment"))
                    .map(Dicom::string)
                    .orElse(null));
        }

        instance.setDate(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("DateTime Started"))
                .flatMap(Dicom::localDateTime)
                .map(LocalDateTime::toLocalDate)
                .orElse(null));

        instance.setTime(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("DateTime Started"))
                .flatMap(Dicom::localDateTime)
                .map(LocalDateTime::toLocalTime)
                .orElse(null));

        instance.setProtocol(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("Acquisition Protocol"))
                .map(Dicom::string)
                .orElse(null));

        if (instance.getProtocol() == null) {
            instance.setProtocol(dicom
                    .first("ContentSequence")
                    .flatMap(d -> d.first("CT Acquisition"))
                    .flatMap(d -> d.first("Acquisition Protocol"))
                    .map(Dicom::string)
                    .orElse(null));
        }

        instance.setType(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("Irradiation Event Type"))
                .map(Dicom::string)
                .orElse(null));

        if (instance.getType() == null) {
            instance.setType(dicom
                    .first("ContentSequence")
                    .flatMap(d -> d.first("CT Acquisition"))
                    .flatMap(d -> d.first("CT Acquisition Type"))
                    .map(Dicom::string)
                    .orElse(null));
        }

        instance.setPlane(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("Acquisition Plane"))
                .map(Dicom::string)
                .orElse(null));

        instance.setRegion(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("Target Region"))
                .map(Dicom::string)
                .orElse(null));

        if (instance.getRegion() == null) {
            instance.setRegion(dicom
                    .first("ContentSequence")
                    .flatMap(d -> d.first("CT Acquisition"))
                    .flatMap(d -> d.first("Target Region"))
                    .map(Dicom::string)
                    .orElse(null));
        }

        instance.setAnatomy(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("Anatomical structure"))
                .map(Dicom::string)
                .orElse(null));

        instance.setLaterality(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("Anatomical structure"))
                .flatMap(d -> d.first("Laterality"))
                .flatMap(Dicom::laterality)
                .orElse(null));

        instance.setView(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("Image View"))
                .map(Dicom::string)
                .orElse(null));

        instance.setOrientation(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("Patient Orientation"))
                .map(Dicom::string)
                .orElse(null));

        instance.setExposureTime(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("Exposure Time"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        if (instance.getExposureTime() == null) {
            instance.setExposureTime(dicom
                    .first("ContentSequence")
                    .flatMap(d -> d.first("CT Acquisition"))
                    .flatMap(d -> d.first("CT Acquisition Parameters"))
                    .flatMap(d -> d.first("Exposure Time"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));
        }

        instance.setExposureTimeRotation(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("CT Acquisition"))
                .flatMap(d -> d.first("CT Acquisition Parameters"))
                .flatMap(d -> d.first("CT X-Ray Source Parameters"))
                .flatMap(d -> d.first("Exposure Time per Rotation"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        instance.setDuration(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("Irradiation Duration"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        instance.setScanningLength(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("CT Acquisition"))
                .flatMap(d -> d.first("CT Acquisition Parameters"))
                .flatMap(d -> d.first("Scanning Length"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        // todo review this is apparently ms
        //instance.setPulseWidth();

        instance.setPulseRate(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("Pulse Rate"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        instance.setVoltagePeak(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("KVP"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        if (instance.getVoltagePeak() == null) {
            instance.setVoltagePeak(dicom
                    .first("ContentSequence")
                    .flatMap(d -> d.first("CT Acquisition"))
                    .flatMap(d -> d.first("CT Acquisition Parameters"))
                    .flatMap(d -> d.first("CT X-Ray Source Parameters"))
                    .flatMap(d -> d.first("KVP"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));
        }

        instance.setTubeCurrent(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("X-Ray Tube Current"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        if (instance.getTubeCurrent() == null) {
            instance.setTubeCurrent(dicom
                    .first("ContentSequence")
                    .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                    .flatMap(d -> d.first("Average X-Ray Tube Current"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));
        }
        // todo not sure
        if (instance.getTubeCurrent() == null) {
            instance.setTubeCurrent(dicom
                    .first("ContentSequence")
                    .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                    .flatMap(d -> d.first("mA"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));
        }

        if (instance.getTubeCurrent() == null) {
            instance.setTubeCurrent(dicom
                    .first("ContentSequence")
                    .flatMap(d -> d.first("CT Acquisition"))
                    .flatMap(d -> d.first("CT Acquisition Parameters"))
                    .flatMap(d -> d.first("CT X-Ray Source Parameters"))
                    .flatMap(d -> d.first("X-Ray Tube Current"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));
        }

        instance.setTubeCurrentPeak(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("CT Acquisition"))
                .flatMap(d -> d.first("CT Acquisition Parameters"))
                .flatMap(d -> d.first("CT X-Ray Source Parameters"))
                .flatMap(d -> d.first("Maximum X-ray Tube Current"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        instance.setTubeCurrentTime(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("Exposure"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        instance.setDoseAreaProduct(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("Dose Area Product"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        instance.setDoseLengthProduct(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("CT Acquisition"))
                .flatMap(d -> d.first("CT Dose"))
                .flatMap(d -> d.first("DLP"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        instance.setDoseIndexVolume(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("CT Acquisition"))
                .flatMap(d -> d.first("CT Dose"))
                .flatMap(d -> d.first("Mean CTDIvol"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        instance.setDoseReferencePoint(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("Dose (RP)"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        instance.setDoseOrgan(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("OrganDose"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        instance.setModulation(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("CT Acquisition"))
                .flatMap(d -> d.first("Dose Reduce Parameters"))
                .flatMap(d -> d.first("Modulation"))
                .map(Dicom::string)
                .orElse(null));

        if (instance.getModality() == null) {
            instance.setModulation(dicom
                    .first("ContentSequence")
                    .flatMap(d -> d.first("CT Acquisition"))
                    .flatMap(d -> d.first("X-Ray Modulation Type"))
                    .map(Dicom::string)
                    .orElse(null));
        }

        instance.setCompressionThickness(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("Compression Thickness"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        return instance;
    }
}
