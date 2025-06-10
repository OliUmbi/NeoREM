package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.entities.Patient;
import ch.oliumbi.neorem.entities.Study;
import ch.oliumbi.neorem.repositories.PatientRepository;
import ch.oliumbi.neorem.repositories.StudyRepository;
import ch.oliumbi.neorem.specifications.PatientSpecification;
import ch.oliumbi.neorem.specifications.StudySpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final StudyRepository studyRepository;

    public PatientService(PatientRepository patientRepository, StudyRepository studyRepository) {
        this.patientRepository = patientRepository;
        this.studyRepository = studyRepository;
    }

    public Patient byId(UUID id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        patient.setStudies(studyRepository.findAllByPatientId(patient.getId()));

        return patient;
    }

    public Page<Patient> all(Pageable pageable, String externalId, String externalIssuer) {
        return patientRepository.findAll(PatientSpecification.filterAll(externalId, externalIssuer), pageable);
    }
}
