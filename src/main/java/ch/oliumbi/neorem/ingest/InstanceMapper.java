package ch.oliumbi.neorem.ingest;

import ch.oliumbi.neorem.entities.Instance;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class InstanceMapper {

    // todo split up
    // todo create instance object
    // todo add support for image files
    // todo search for secondary values or cases where information needs to be extracted elsewhere
    // todo maybe restructure the value retrieval because it is kinda big

    public List<Instance> mapIrradiationEvent(Dicom dicom) {
        List<Instance> instances = new ArrayList<>();

        List<Dicom> irradiationEvents = dicom
                .first("ContentSequence")
                .map(d -> d.all("Irradiation Event X-Ray Data"))
                .orElse(Collections.emptyList());

        if (irradiationEvents.isEmpty()) {
            return instances;
        }

        for (Dicom irradiationEvent : irradiationEvents) {
            Instance instance = new Instance();

            instance.setEventId(irradiationEvent
                    .first("Irradiation Event UID")
                    .flatMap(Dicom::string)
                    .orElse(null));

            instance.setModality(irradiationEvent
                    .first("Irradiation Event Type")
                    .flatMap(Dicom::modality)
                    .orElse(null));

            instance.setComment(irradiationEvent
                    .first("Comment")
                    .flatMap(Dicom::string)
                    .orElse(null));

            instance.setDate(irradiationEvent
                    .first("DateTime Started")
                    .flatMap(Dicom::localDateTime)
                    .map(LocalDateTime::toLocalDate)
                    .orElse(null));

            instance.setTime(irradiationEvent
                    .first("DateTime Started")
                    .flatMap(Dicom::localDateTime)
                    .map(LocalDateTime::toLocalTime)
                    .orElse(null));

            instance.setProtocol(irradiationEvent
                    .first("Acquisition Protocol")
                    .flatMap(Dicom::string)
                    .orElse(null));

            instance.setType(irradiationEvent
                    .first("Irradiation Event Type")
                    .flatMap(Dicom::string)
                    .orElse(null));

            instance.setPlane(irradiationEvent
                    .first("Acquisition Plane")
                    .flatMap(Dicom::string)
                    .orElse(null));

            instance.setRegion(irradiationEvent
                    .first("Target Region")
                    .flatMap(Dicom::string)
                    .orElse(null));

            instance.setAnatomy(irradiationEvent
                    .first("Anatomical structure")
                    .flatMap(Dicom::string)
                    .orElse(null));

            instance.setLaterality(irradiationEvent
                    .first("Anatomical structure")
                    .flatMap(d -> d.first("Laterality"))
                    .flatMap(Dicom::laterality)
                    .orElse(null));

            instance.setView(irradiationEvent
                    .first("Image View")
                    .flatMap(Dicom::string)
                    .orElse(null));

            instance.setOrientation(irradiationEvent
                    .first("Patient Orientation")
                    .flatMap(Dicom::string)
                    .orElse(null));

            instance.setExposureTime(irradiationEvent
                    .first("Exposure Time")
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            instance.setDuration(irradiationEvent
                    .first("Irradiation Duration")
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            instance.setPulseRate(irradiationEvent
                    .first("Pulse Rate")
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            instance.setVoltagePeak(irradiationEvent
                    .first("KVP")
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            instance.setTubeCurrent(irradiationEvent
                    .first("X-Ray Tube Current")
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            if (instance.getTubeCurrent() == null) {
                instance.setTubeCurrent(irradiationEvent
                        .first("Average X-Ray Tube Current")
                        .flatMap(Dicom::floatingPoint)
                        .orElse(null));
            }
            // todo not sure
            if (instance.getTubeCurrent() == null) {
                instance.setTubeCurrent(irradiationEvent
                        .first("mA")
                        .flatMap(Dicom::floatingPoint)
                        .orElse(null));
            }

            // todo mammography merge information with mean and report peaks where possible like tubeCurrentPeak
            // todo mammography maybe report all filters used or just the dominant one because there wont be any else in 99.9% cases or is irrelevant
            // todo pulse width is pulse time (ms) used for mammo

            instance.setTubeCurrentTime(irradiationEvent
                    .first("Exposure")
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            instance.setDoseAreaProduct(irradiationEvent
                    .first("Dose Area Product")
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            instance.setDoseReferencePoint(irradiationEvent
                    .first("Dose (RP)")
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            // todo not sure
            instance.setDoseOrgan(irradiationEvent
                    .first("OrganDose")
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            instance.setCompressionThickness(irradiationEvent
                    .first("Compression Thickness")
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            instances.add(instance);
        }

        return instances;
    }

    public List<Instance> mapComputedTomographyAcquisition(Dicom dicom) {
        List<Instance> instances = new ArrayList<>();

        List<Dicom> computedTomographyAcquisitions = dicom
                .first("ContentSequence")
                .map(d -> d.all("CT Acquisition"))
                .orElse(Collections.emptyList());

        if (computedTomographyAcquisitions.isEmpty()) {
            return instances;
        }

        for (Dicom computedTomographyAcquisition : computedTomographyAcquisitions) {
            Instance instance = new Instance();

            instance.setEventId(computedTomographyAcquisition
                    .first("Irradiation Event UID")
                    .flatMap(Dicom::string)
                    .orElse(null));

            instance.setComment(computedTomographyAcquisition
                    .first("Comment")
                    .flatMap(Dicom::string)
                    .orElse(null));

            instance.setProtocol(computedTomographyAcquisition
                    .first("Acquisition Protocol")
                    .flatMap(Dicom::string)
                    .orElse(null));

            instance.setType(computedTomographyAcquisition
                    .first("CT Acquisition Type")
                    .flatMap(Dicom::string)
                    .orElse(null));

            instance.setRegion(computedTomographyAcquisition
                    .first("Target Region")
                    .flatMap(Dicom::string)
                    .orElse(null));

            instance.setExposureTime(computedTomographyAcquisition
                    .first("CT Acquisition Parameters")
                    .flatMap(d -> d.first("Exposure Time"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            instance.setExposureTimeRotation(computedTomographyAcquisition
                    .first("CT Acquisition Parameters")
                    .flatMap(d -> d.first("CT X-Ray Source Parameters"))
                    .flatMap(d -> d.first("Exposure Time per Rotation"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            instance.setScanningLength(computedTomographyAcquisition
                    .first("CT Acquisition Parameters")
                    .flatMap(d -> d.first("Scanning Length"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            instance.setVoltagePeak(computedTomographyAcquisition
                    .first("CT Acquisition Parameters")
                    .flatMap(d -> d.first("CT X-Ray Source Parameters"))
                    .flatMap(d -> d.first("KVP"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            instance.setTubeCurrent(computedTomographyAcquisition
                    .first("CT Acquisition Parameters")
                    .flatMap(d -> d.first("CT X-Ray Source Parameters"))
                    .flatMap(d -> d.first("X-Ray Tube Current"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            instance.setTubeCurrentPeak(computedTomographyAcquisition
                    .first("CT Acquisition Parameters")
                    .flatMap(d -> d.first("CT X-Ray Source Parameters"))
                    .flatMap(d -> d.first("Maximum X-ray Tube Current"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            instance.setDoseLengthProduct(computedTomographyAcquisition
                    .first("CT Dose")
                    .flatMap(d -> d.first("DLP"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            instance.setDoseIndexVolume(computedTomographyAcquisition
                    .first("CT Dose")
                    .flatMap(d -> d.first("Mean CTDIvol"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            instance.setModulation(computedTomographyAcquisition
                    .first("Dose Reduce Parameters")
                    .flatMap(d -> d.first("Modulation"))
                    .flatMap(Dicom::string)
                    .orElse(null));

            instance.setModulation(computedTomographyAcquisition
                    .first("X-Ray Modulation Type")
                    .flatMap(Dicom::string)
                    .orElse(null));

            instances.add(instance);
        }

        return instances;
    }

    public List<Instance> mapOrganDose(Dicom dicom) {
        List<Instance> instances = new ArrayList<>();

        List<Dicom> organDoses = dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Radiopharmaceutical Administration"))
                .map(d -> d.all("Organ Dose Information"))
                .orElse(Collections.emptyList());

        if (organDoses.isEmpty()) {
            return instances;
        }

        for (Dicom organDose : organDoses) {
            Instance instance = new Instance();

            instance.setAnatomy(organDose
                    .first("Finding Site")
                    .flatMap(Dicom::string)
                    .orElse(null));

            instance.setLaterality(organDose
                    .first("Laterality")
                    .flatMap(Dicom::laterality)
                    .orElse(null));

            instance.setDoseOrgan(organDose
                    .first("Organ Dose")
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));

            instances.add(instance);
        }

        return instances;
    }
}
