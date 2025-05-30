package ch.oliumbi.neorem.ingest;

import ch.oliumbi.neorem.entities.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public Patient map(Patient patient, Dicom dicom) {
        patient.setExternalId(dicom
                .getFirst("PatientID")
                .map(Dicom::string)
                .orElse(null));

        patient.setExternalIssuer(dicom
                .getFirst("IssuerOfPatientID")
                .map(Dicom::string)
                .orElse(null));

        patient.setSex(dicom
                .getFirst("PatientSex")
                .map(Dicom::string)
                .orElse(null));

        return patient;
    }
}
