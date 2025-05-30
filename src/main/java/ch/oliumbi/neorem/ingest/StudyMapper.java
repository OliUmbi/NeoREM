package ch.oliumbi.neorem.ingest;

import ch.oliumbi.neorem.entities.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class StudyMapper {

    public Study map(Study study, Dicom dicom) {

        study.setModality(dicom
                .getFirst("Modality")
                .map(Dicom::string)
                .orElse(null));

        study.setInstanceId(dicom
                .getFirst("SOPInstanceUID")
                .map(Dicom::string)
                .orElse(null));

        study.setAccessionId(dicom
                .getFirst("AccessionNumber")
                .map(Dicom::string)
                .orElse(null));

        study.setDate(dicom
                .getFirst("StudyDate")
                .flatMap(Dicom::localDate)
                .orElse(null));

        study.setTime(dicom
                .getFirst("StudyTime")
                .flatMap(Dicom::localTime)
                .orElse(null));

        study.setDescription(dicom
                .getFirst("StudyDescription")
                .map(Dicom::string)
                .orElse(null));

        study.setReason(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Procedure reported"))
                .flatMap(d -> d.getFirst("Has Intent"))
                .map(Dicom::string)
                .orElse(null));

        study.setRequestedProcedure(dicom
                .getFirst("RequestedProcedureDescription")
                .map(Dicom::string)
                .orElse(null));

        study.setPerformedProcedure(dicom
                .getFirst("PerformedProcedureStepDescription")
                .map(Dicom::string)
                .orElse(null));

        study.setInstitution(dicom
                .getFirst("InstitutionName")
                .map(Dicom::string)
                .orElse(null));

        study.setDepartment(dicom
                .getFirst("InstitutionalDepartmentName")
                .map(Dicom::string)
                .orElse(null));

        study.setStation(dicom
                .getFirst("StationName")
                .map(Dicom::string)
                .orElse(null));

        study.setPhysicians(dicom
                .getFirst("PerformingPhysicianName")
                .map(Dicom::string)
                .orElse(null)); // todo add all physicians

        study.setOperators(dicom
                .getFirst("OperatorsName")
                .map(Dicom::string)
                .orElse(null));

        study.setHeight(dicom
                .getFirst("PatientSize")
                .flatMap(Dicom::height)
                .orElse(null));

        study.setWeight(dicom
                .getFirst("PatientWeight")
                .flatMap(Dicom::integer)
                .orElse(null));

        study.setBodyMassIndex(dicom
                .getFirst("PatientBodyMassIndex")
                .flatMap(Dicom::floatingPoint)
                .orElse(null)); // todo calculate if not present

        study.setAge(dicom
                .getFirst("PatientAge")
                .flatMap(Dicom::age)
                .orElse(null));

        study.setPregnancy(dicom
                .getFirst("PregnancyStatus")
                .flatMap(Dicom::pregnancy)
                .orElse(null));

        study.setComment(dicom
                .getFirst("CommentsOnRadiationDose")
                .map(Dicom::string)
                .orElse(null));

        return study;
    }

    public StudyComputedTomography mapComputedTomography(StudyComputedTomography studyComputedTomography, Dicom dicom) {

        studyComputedTomography.setEvents(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("CT Accumulated Dose Data"))
                .flatMap(d -> d.getFirst("Total Number of Irradiation Events"))
                .flatMap(Dicom::integer)
                .orElse(null));

        studyComputedTomography.setDoseAreaProduct(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("CT Accumulated Dose Data"))
                .flatMap(d -> d.getFirst("CT Dose Length Product Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));
        
        return studyComputedTomography;
    }

    public StudyFluoroscopy mapFluoroscopy(StudyFluoroscopy studyFluoroscopy, Dicom dicom) {

        studyFluoroscopy.setDoseAreaProductFluoroscopy(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.getFirst("Fluoro Dose Area Product Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyFluoroscopy.setDoseAreaProductAcquisition(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.getFirst("Acquisition Dose Area Product Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyFluoroscopy.setDoseAreaProductTotal(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.getFirst("Dose Area Product Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyFluoroscopy.setDoseReferencePointFluoroscopy(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.getFirst("Fluoro Dose (RP) Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyFluoroscopy.setDoseReferencePointAcquisition(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.getFirst("Acquisition Dose (RP) Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyFluoroscopy.setDoseReferencePointTotal(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.getFirst("Dose (RP) Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyFluoroscopy.setDurationFluoroscopy(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.getFirst("Total Fluoro Time"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyFluoroscopy.setDurationAcquisition(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.getFirst("Total Acquisition Time"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyFluoroscopy.setReferencePointDefinition(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.getFirst("Reference Point Definition"))
                .map(Dicom::string)
                .orElse(null));

        return studyFluoroscopy;
    }

    public StudyMammography mapMammography(StudyMammography studyMammography, Dicom dicom) {

        List<Dicom> averageGlandularDoses = dicom
                .getFirst("ContentSequence")
                .map(d -> d.get("Accumulated Average Glandular Dose"))
                .orElse(Collections.emptyList());

        if (averageGlandularDoses.size() == 2) {
            for (Dicom averageGlandularDose : averageGlandularDoses) {
                String laterality = averageGlandularDose.getFirst("Laterality").map(Dicom::string).orElse("");

                if (StringUtils.containsIgnoreCase(laterality, "left")) {
                    studyMammography.setAverageGlandularDoseLeft(averageGlandularDose.floatingPoint().orElse(null));
                }

                if (StringUtils.containsIgnoreCase(laterality, "right")) {
                    studyMammography.setAverageGlandularDoseRight(averageGlandularDose.floatingPoint().orElse(null));
                }
            }
        }

        return studyMammography;
    }

    public StudyNuclearMedicine mapNuclearMedicine(StudyNuclearMedicine studyNuclearMedicine, Dicom dicom) {

        studyNuclearMedicine.setEffectiveDose(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Radiopharmaceutical Administration"))
                .flatMap(d -> d.getFirst("Effective Dose Information"))
                .flatMap(d -> d.getFirst("Effective Dose"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyNuclearMedicine.setAgent(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Radiopharmaceutical Administration"))
                .flatMap(d -> d.getFirst("Radiopharmaceutical agent"))
                .map(Dicom::string)
                .orElse(null));

        studyNuclearMedicine.setRadionuclide(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Radiopharmaceutical Administration"))
                .flatMap(d -> d.getFirst("Radiopharmaceutical agent"))
                .flatMap(d -> d.getFirst("Radionuclide"))
                .map(Dicom::string)
                .orElse(null));

        studyNuclearMedicine.setHalfLife(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Radiopharmaceutical Administration"))
                .flatMap(d -> d.getFirst("Radiopharmaceutical agent"))
                .flatMap(d -> d.getFirst("Radionuclide Half Life"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyNuclearMedicine.setTimeStart(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Radiopharmaceutical Administration"))
                .flatMap(d -> d.getFirst("Radiopharmaceutical Start DateTime"))
                .flatMap(Dicom::localDateTime)
                .map(LocalDateTime::toLocalTime)
                .orElse(null));

        studyNuclearMedicine.setTimeEnd(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Radiopharmaceutical Administration"))
                .flatMap(d -> d.getFirst("Radiopharmaceutical Stop DateTime"))
                .flatMap(Dicom::localDateTime)
                .map(LocalDateTime::toLocalTime)
                .orElse(null));

        studyNuclearMedicine.setActivity(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Radiopharmaceutical Administration"))
                .flatMap(d -> d.getFirst("Administered activity"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyNuclearMedicine.setRoute(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Radiopharmaceutical Administration"))
                .flatMap(d -> d.getFirst("Route of administration"))
                .map(Dicom::string)
                .orElse(null));

        studyNuclearMedicine.setLaterality(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Radiopharmaceutical Administration"))
                .flatMap(d -> d.getFirst("Route of administration"))
                .flatMap(d -> d.getFirst("Site of"))
                .map(Dicom::string)
                .orElse(null));

        return studyNuclearMedicine;
    }

    public StudyRadiography mapRadiography(StudyRadiography studyRadiography, Dicom dicom) {

        studyRadiography.setEvents(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.getFirst("Total Number of Radiographic Frames"))
                .flatMap(Dicom::integer)
                .orElse(null));

        studyRadiography.setDoseAreaProduct(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.getFirst("Dose Area Product Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyRadiography.setDoseReferencePointTotal(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.getFirst("Dose (RP) Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyRadiography.setReferencePointDefinition(dicom
                .getFirst("ContentSequence")
                .flatMap(d -> d.getFirst("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.getFirst("Reference Point Definition"))
                .map(Dicom::string)
                .orElse(null));

        return studyRadiography;
    }
}
