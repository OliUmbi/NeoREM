package ch.oliumbi.neorem.mappers;

import ch.oliumbi.neorem.entities.*;
import ch.oliumbi.neorem.ingest.Dicom;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component
public class StudyMapper {

    public Study map(Dicom dicom) {

        Study study = new Study();

        study.setModality(dicom
                .first("Modality")
                .flatMap(Dicom::modality)
                .orElse(null));

        study.setExternalId(dicom
                .first("StudyInstanceUID")
                .flatMap(Dicom::string)
                .orElse(null));

        study.setAccessionId(dicom
                .first("AccessionNumber")
                .flatMap(Dicom::string)
                .orElse(null));

        study.setDate(dicom
                .first("StudyDate")
                .flatMap(Dicom::localDate)
                .orElse(null));

        study.setTime(dicom
                .first("StudyTime")
                .flatMap(Dicom::localTime)
                .orElse(null));

        study.setDescription(dicom
                .first("StudyDescription")
                .flatMap(Dicom::string)
                .orElse(null));

        if (study.getDescription() == null) {
            study.setDescription(dicom
                    .first("SeriesDescription")
                    .flatMap(Dicom::string)
                    .orElse(null));
        }

        study.setReason(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Procedure reported"))
                .flatMap(d -> d.first("Has Intent"))
                .flatMap(Dicom::string)
                .orElse(null));

        study.setProcedureRequested(dicom
                .first("RequestedProcedureDescription")
                .flatMap(Dicom::string)
                .orElse(null));

        study.setProcedurePerformed(dicom
                .first("PerformedProcedureStepDescription")
                .flatMap(Dicom::string)
                .orElse(null));

        study.setInstitution(dicom
                .first("InstitutionName")
                .flatMap(Dicom::string)
                .orElse(null));

        study.setDepartment(dicom
                .first("InstitutionalDepartmentName")
                .flatMap(Dicom::string)
                .orElse(null));

        study.setStation(dicom
                .first("StationName")
                .flatMap(Dicom::string)
                .orElse(null));

        // todo add all physicians
        study.setPhysicians(dicom
                .first("PerformingPhysicianName")
                .flatMap(Dicom::string)
                .orElse(null));

        study.setOperators(dicom
                .first("OperatorsName")
                .flatMap(Dicom::string)
                .orElse(null));

        study.setHeight(dicom
                .first("PatientSize")
                .flatMap(Dicom::height)
                .orElse(null));

        study.setWeight(dicom
                .first("PatientWeight")
                .flatMap(Dicom::integer)
                .orElse(null));

        // todo calculate if not present
        study.setBodyMassIndex(dicom
                .first("PatientBodyMassIndex")
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

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
                .flatMap(Dicom::string)
                .orElse(null));

        if (study.getComment() == null) {
            study.setComment(dicom
                    .first("ContentSequence")
                    .flatMap(d -> d.first("Comment"))
                    .flatMap(Dicom::string)
                    .orElse(null));
        }

        if (study.getComment() == null) {
            study.setComment(dicom
                    .first("PatientComments")
                    .flatMap(Dicom::string)
                    .orElse(null));
        }

        study.setEventsAmount(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Total Number of Radiographic Frames"))
                .flatMap(Dicom::integer)
                .orElse(null));

        if (study.getEventsAmount() == null) {
            study.setEventsAmount(dicom
                    .first("ContentSequence")
                    .flatMap(d -> d.first("CT Accumulated Dose Data"))
                    .flatMap(d -> d.first("Total Number of Irradiation Events"))
                    .flatMap(Dicom::integer)
                    .orElse(null));
        }

        study.setDoseLengthProduct(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("CT Accumulated Dose Data"))
                .flatMap(d -> d.first("CT Dose Length Product Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        List<Dicom> averageGlandularDoses = dicom
                .first("ContentSequence")
                .map(d -> d.all("Accumulated Average Glandular Dose"))
                .orElse(Collections.emptyList());

        if (averageGlandularDoses.size() == 2) {
            for (Dicom averageGlandularDose : averageGlandularDoses) {
                String laterality = averageGlandularDose.first("Laterality").flatMap(Dicom::string).orElse(null);

                if (StringUtils.containsIgnoreCase(laterality, "left")) {
                    study.setAverageGlandularDoseLeft(averageGlandularDose.floatingPoint().orElse(null));
                }

                if (StringUtils.containsIgnoreCase(laterality, "right")) {
                    study.setAverageGlandularDoseRight(averageGlandularDose.floatingPoint().orElse(null));
                }
            }
        }

        // todo check if correct
        study.setDurationTotal(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Total Time"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        study.setDurationFluoroscopy(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Total Fluoro Time"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        study.setDurationAcquisition(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Total Acquisition Time"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        study.setDoseAreaProductTotal(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Dose Area Product Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        study.setDoseAreaProductFluoroscopy(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Fluoro Dose Area Product Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        study.setDoseAreaProductAcquisition(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Acquisition Dose Area Product Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        study.setDoseReferencePointTotal(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Dose (RP) Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        if (study.getDoseReferencePointTotal() == null) {
            study.setDoseReferencePointTotal(dicom
                    .first("ContentSequence")
                    .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                    .flatMap(d -> d.first("Dose(RP) Total"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));
        }

        study.setDoseReferencePointFluoroscopy(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Fluoro Dose (RP) Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        if (study.getDoseReferencePointFluoroscopy() == null) {
            study.setDoseReferencePointFluoroscopy(dicom
                    .first("ContentSequence")
                    .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                    .flatMap(d -> d.first("Fluoro Dose(RP) Total"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));
        }

        study.setDoseReferencePointAcquisition(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                .flatMap(d -> d.first("Acquisition Dose (RP) Total"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        if (study.getDoseReferencePointAcquisition() == null) {
            study.setDoseReferencePointAcquisition(dicom
                    .first("ContentSequence")
                    .flatMap(d -> d.first("Accumulated X-Ray Dose Data"))
                    .flatMap(d -> d.first("Acquisition Dose(RP) Total"))
                    .flatMap(Dicom::floatingPoint)
                    .orElse(null));
        }

        study.setPharmaceuticalAgent(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Radiopharmaceutical Administration"))
                .flatMap(d -> d.first("Radiopharmaceutical agent"))
                .flatMap(Dicom::string)
                .orElse(null));

        study.setPharmaceuticalRadionuclide(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Radiopharmaceutical Administration"))
                .flatMap(d -> d.first("Radiopharmaceutical agent"))
                .flatMap(d -> d.first("Radionuclide"))
                .flatMap(Dicom::string)
                .orElse(null));

        study.setPharmaceuticalHalfLife(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Radiopharmaceutical Administration"))
                .flatMap(d -> d.first("Radiopharmaceutical agent"))
                .flatMap(d -> d.first("Radionuclide Half Life"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        study.setPharmaceuticalActivity(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Radiopharmaceutical Administration"))
                .flatMap(d -> d.first("Administered activity"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        study.setPharmaceuticalDose(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Radiopharmaceutical Administration"))
                .flatMap(d -> d.first("Effective Dose Information"))
                .flatMap(d -> d.first("Effective Dose"))
                .flatMap(Dicom::floatingPoint)
                .orElse(null));

        study.setPharmaceuticalRoute(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Radiopharmaceutical Administration"))
                .flatMap(d -> d.first("Route of administration"))
                .flatMap(Dicom::string)
                .orElse(null));

        // todo check lateralities
        study.setPharmaceuticalLaterality(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Radiopharmaceutical Administration"))
                .flatMap(d -> d.first("Route of administration"))
                .flatMap(d -> d.first("Site of"))
                .flatMap(Dicom::string)
                .orElse(null));

        study.setPharmaceuticalTime(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Radiopharmaceutical Administration"))
                .flatMap(d -> d.first("Radiopharmaceutical Start DateTime"))
                .flatMap(Dicom::localDateTime)
                .map(LocalDateTime::toLocalTime)
                .orElse(null));

        study.setPharmaceuticalComment(dicom
                .first("ContentSequence")
                .flatMap(d -> d.first("Radiopharmaceutical Administration"))
                .flatMap(d -> d.first("Comment -> {HashMap@2004} size = 0"))
                .flatMap(Dicom::string)
                .orElse(null));

        return study;
    }
}
