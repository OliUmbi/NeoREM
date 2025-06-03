package ch.oliumbi.neorem.ingest;

import ch.oliumbi.neorem.entities.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component
public class StudyMapper {

    public Study map(Study study, Dicom dicom) {

        study.setModality(dicom
                .first("Modality")
                .map(Dicom::string)
                .orElse(null));

        study.setInstanceId(dicom
                .first("SOPInstanceUID")
                .map(Dicom::string)
                .orElse(null));

        study.setAccessionId(dicom
                .first("AccessionNumber")
                .map(Dicom::string)
                .orElse(null));

        study.setDate(dicom
                .first("StudyDate")
                .flatMap(Dicom::localDate)
                .orElse(null));

        study.setTime(dicom
                .first("StudyTime")
                .flatMap(Dicom::localTime)
                .orElse(null));

        // todo fallback to series description
        study.setDescription(dicom
                .first("StudyDescription")
                .map(Dicom::string)
                .orElse(null));

        // todo rdsr specific
        study.setReason(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Procedure reported"))
                .flatMap(d -> d.first("Has Intent"))
                .map(Dicom::string)
                .orElse(null));

        study.setRequestedProcedure(dicom
                .first("RequestedProcedureDescription")
                .map(Dicom::string)
                .orElse(null));

        study.setPerformedProcedure(dicom
                .first("PerformedProcedureStepDescription")
                .map(Dicom::string)
                .orElse(null));

        study.setInstitution(dicom
                .first("InstitutionName")
                .map(Dicom::string)
                .orElse(null));

        study.setDepartment(dicom
                .first("InstitutionalDepartmentName")
                .map(Dicom::string)
                .orElse(null));

        study.setStation(dicom
                .first("StationName")
                .map(Dicom::string)
                .orElse(null));

        study.setPhysicians(dicom
                .first("PerformingPhysicianName")
                .map(Dicom::string)
                .orElse(null)); // todo add all physicians

        study.setOperators(dicom
                .first("OperatorsName")
                .map(Dicom::string)
                .orElse(null));

        study.setHeight(dicom
                .first("PatientSize")
                .flatMap(Dicom::height)
                .orElse(null));

        study.setWeight(dicom
                .first("PatientWeight")
                .flatMap(Dicom::integer)
                .orElse(null));

        study.setBodyMassIndex(dicom
                .first("PatientBodyMassIndex")
                .flatMap(Dicom::floatingPoint)
                .orElse(null)); // todo calculate if not present

        study.setAge(dicom
                .first("PatientAge")
                .flatMap(Dicom::age)
                .orElse(null));

        study.setPregnancy(dicom
                .first("PregnancyStatus")
                .flatMap(Dicom::pregnancy)
                .orElse(null));

        study.setComment(dicom
                .first("CommentsOnRadiationDose")
                .map(Dicom::string)
                .orElse(null));

        return study;
    }

    // todo rdsr specific
    public StudyComputedTomography mapComputedTomography(StudyComputedTomography studyComputedTomography, Dicom dicom) {

        studyComputedTomography.setEvents(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("CT Accumulated Dose Data"))
                .flatMap(d -> d.first("Total Number of Irradiation Events"))
                .flatMap(Dicom::integer)
                .orElse(null));

        studyComputedTomography.setDoseAreaProduct(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("CT Accumulated Dose Data"))
                .flatMap(d -> d.first("CT Dose Length Product Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));
        
        return studyComputedTomography;
    }

    // todo rdsr specific
    public StudyFluoroscopy mapFluoroscopy(StudyFluoroscopy studyFluoroscopy, Dicom dicom) {

        studyFluoroscopy.setDoseAreaProductFluoroscopy(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Fluoro Dose Area Product Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyFluoroscopy.setDoseAreaProductAcquisition(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Acquisition Dose Area Product Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyFluoroscopy.setDoseAreaProductTotal(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Dose Area Product Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyFluoroscopy.setDoseReferencePointFluoroscopy(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Fluoro Dose (RP) Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyFluoroscopy.setDoseReferencePointAcquisition(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Acquisition Dose (RP) Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyFluoroscopy.setDoseReferencePointTotal(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Dose (RP) Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyFluoroscopy.setDurationFluoroscopy(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Total Fluoro Time"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyFluoroscopy.setDurationAcquisition(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Total Acquisition Time"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyFluoroscopy.setReferencePointDefinition(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Reference Point Definition"))
                .map(Dicom::string)
                .orElse(null));

        return studyFluoroscopy;
    }

    // todo rdsr specific
    public StudyMammography mapMammography(StudyMammography studyMammography, Dicom dicom) {

        List<Dicom> averageGlandularDoses = dicom
                .first("ContentSequence")
                .map(d -> d.all("Accumulated Average Glandular Dose"))
                .orElse(Collections.emptyList());

        if (averageGlandularDoses.size() == 2) {
            for (Dicom averageGlandularDose : averageGlandularDoses) {
                String laterality = averageGlandularDose.first("Laterality").map(Dicom::string).orElse("");

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

    // todo rdsr specific
    public StudyNuclearMedicine mapNuclearMedicine(StudyNuclearMedicine studyNuclearMedicine, Dicom dicom) {

        studyNuclearMedicine.setEffectiveDose(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Radiopharmaceutical Administration"))
                .flatMap(d -> d.first("Effective Dose Information"))
                .flatMap(d -> d.first("Effective Dose"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyNuclearMedicine.setAgent(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Radiopharmaceutical Administration"))
                .flatMap(d -> d.first("Radiopharmaceutical agent"))
                .map(Dicom::string)
                .orElse(null));

        studyNuclearMedicine.setRadionuclide(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Radiopharmaceutical Administration"))
                .flatMap(d -> d.first("Radiopharmaceutical agent"))
                .flatMap(d -> d.first("Radionuclide"))
                .map(Dicom::string)
                .orElse(null));

        studyNuclearMedicine.setHalfLife(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Radiopharmaceutical Administration"))
                .flatMap(d -> d.first("Radiopharmaceutical agent"))
                .flatMap(d -> d.first("Radionuclide Half Life"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyNuclearMedicine.setTimeStart(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Radiopharmaceutical Administration"))
                .flatMap(d -> d.first("Radiopharmaceutical Start DateTime"))
                .flatMap(Dicom::localDateTime)
                .map(LocalDateTime::toLocalTime)
                .orElse(null));

        studyNuclearMedicine.setTimeEnd(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Radiopharmaceutical Administration"))
                .flatMap(d -> d.first("Radiopharmaceutical Stop DateTime"))
                .flatMap(Dicom::localDateTime)
                .map(LocalDateTime::toLocalTime)
                .orElse(null));

        studyNuclearMedicine.setActivity(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Radiopharmaceutical Administration"))
                .flatMap(d -> d.first("Administered activity"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyNuclearMedicine.setRoute(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Radiopharmaceutical Administration"))
                .flatMap(d -> d.first("Route of administration"))
                .map(Dicom::string)
                .orElse(null));

        studyNuclearMedicine.setLaterality(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Radiopharmaceutical Administration"))
                .flatMap(d -> d.first("Route of administration"))
                .flatMap(d -> d.first("Site of"))
                .map(Dicom::string)
                .orElse(null));

        return studyNuclearMedicine;
    }

    // todo rdsr specific
    public StudyRadiography mapRadiography(StudyRadiography studyRadiography, Dicom dicom) {

        studyRadiography.setEvents(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Total Number of Radiographic Frames"))
                .flatMap(Dicom::integer)
                .orElse(null));

        studyRadiography.setDoseAreaProduct(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Dose Area Product Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyRadiography.setDoseReferencePointTotal(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Dose (RP) Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        studyRadiography.setReferencePointDefinition(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Reference Point Definition"))
                .map(Dicom::string)
                .orElse(null));

        return studyRadiography;
    }
}
