package ch.oliumbi.neorem.ingest;

import ch.oliumbi.neorem.entities.Instance;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InstanceMapper {

    public Instance map(Instance instance, Dicom dicom) {

        instance.setModality(dicom
                .first("ModalitiesInStudy")
                .flatMap(Dicom::modality)
                .orElse(null));

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

        // instance.setDescription();

        instance.setProtocol(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Irradiation Event X-Ray Data"))
                .flatMap(d -> d.first("Acquisition Protocol"))
                .map(Dicom::string)
                .orElse(null));

        instance.setComment(dicom
                .first("CommentsOnRadiationDose")
                .map(Dicom::string)
                .orElse(null));

        return instance;
    }

    // this is for images not rdsr
    public Instance mapMammography(Instance instance, Dicom dicom) {

        instance.setModality(dicom
                .first("Modality")
                .flatMap(Dicom::modality)
                .orElse(null));

        instance.setDate(dicom
                .first("AcquisitionDate")
                .flatMap(Dicom::localDate)
                .orElse(null));

        instance.setTime(dicom
                .first("AcquisitionTime")
                .flatMap(Dicom::localTime)
                .orElse(null));

        // todo review probably not useful maybe SeriesDescription, DerivationDescription or something else
        instance.setDescription(dicom
                .first("SeriesDescription")
                .map(Dicom::string)
                .orElse(null));

        instance.setProtocol(dicom
                .first("ProtocolName")
                .map(Dicom::string)
                .orElse(null));

        // todo review probably not useful
        instance.setComment(dicom
                .first("CommentsOnRadiationDose")
                .map(Dicom::string)
                .orElse(null));

        // todo between Laterality and ImageLaterality
        instance.getInstanceMammography().setLaterality(dicom
                .first("Laterality")
                .flatMap(Dicom::laterality)
                .orElse(null));

        if (instance.getInstanceMammography().getLaterality() == null) {
            instance.getInstanceMammography().setLaterality(dicom
                    .first("ImageLaterality")
                    .flatMap(Dicom::laterality)
                    .orElse(null));
        }

        instance.getInstanceMammography().setView(dicom
                .first("ViewCodeSequence")
                .flatMap(Dicom::first)
                .flatMap(d -> d.first("CodeMeaning"))
                .map(Dicom::string)
                .orElse(null));

        // todo not tested but probably correct
        instance.getInstanceMammography().setViewModifier(dicom
                .first("ViewCodeSequence")
                .flatMap(Dicom::first)
                .flatMap(d -> d.first("ViewModifierCodeSequence"))
                .flatMap(Dicom::first)
                .flatMap(d -> d.first("CodeMeaning"))
                .map(Dicom::string)
                .orElse(null));

        // todo test if correct there is also CompressionForce
        instance.getInstanceMammography().setCompressionThickness(dicom
                .first("BodyPartThickness")
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        instance.getInstanceMammography().setTarget(dicom
                .first("AnodeTargetMaterial")
                .map(Dicom::string)
                .orElse(null));

        instance.getInstanceMammography().setFilter(dicom
                .first("FilterMaterial")
                .map(Dicom::string)
                .orElse(null));

        instance.getInstanceMammography().setVoltagePeak(dicom
                .first("KVP")
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        instance.getInstanceMammography().setTubeCurrent(dicom
                .first("XRayTubeCurrent")
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        instance.getInstanceMammography().setExposureTime(dicom
                .first("ExposureTime")
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        instance.getInstanceMammography().setTubeCurrentTime(dicom
                .first("FilterMaterial")
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        // todo there is also an EntranceDose
        instance.getInstanceMammography().setEntranceSurfaceDose(dicom
                .first("EntranceDoseInmGy")
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        // todo review probably incorrect
        instance.getInstanceMammography().setAverageGlandularDose(dicom
                .first("OrganDose")
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        instance.getInstanceMammography().setExposureControlMode(dicom
                .first("ExposureControlMode")
                .map(Dicom::string)
                .orElse(null));

        instance.getInstanceMammography().setExposureControlModeDescription(dicom
                .first("ExposureControlModeDescription")
                .map(Dicom::string)
                .orElse(null));

        // todo filter

        return instance;
    }
}
