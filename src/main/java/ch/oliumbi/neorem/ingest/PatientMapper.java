package ch.oliumbi.neorem.ingest;

import ch.oliumbi.neorem.entities.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public Patient map(Dicom dicom) {

        Patient patient = new Patient();

        patient.setExternalId(dicom
                .first("PatientID")
                .flatMap(Dicom::string)
                .orElse(null));

        patient.setExternalIssuer(dicom
                .first("IssuerOfPatientID")
                .flatMap(Dicom::string)
                .orElse(null));

        patient.setSex(dicom
                .first("PatientSex")
                .flatMap(Dicom::string)
                .orElse(null));

        return patient;
    }
}
