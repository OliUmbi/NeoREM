package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.entities.Patient;
import ch.oliumbi.neorem.repositories.PatientRepository;
import ch.oliumbi.neorem.repositories.StudyRepository;
import ch.oliumbi.neorem.specifications.PatientSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final StudyRepository studyRepository;

    public PatientService(PatientRepository patientRepository, StudyRepository studyRepository) {
        this.patientRepository = patientRepository;
        this.studyRepository = studyRepository;
    }

    public Page<Patient> all(Pageable pageable, String externalId, String externalIssuer) {
        return patientRepository.findAll(PatientSpecification.filterAll(externalId, externalIssuer), pageable);
    }

    public Patient byId(UUID id) {
        return patientRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void delete(UUID id) {
        patientRepository.deleteById(id);
    }
}
