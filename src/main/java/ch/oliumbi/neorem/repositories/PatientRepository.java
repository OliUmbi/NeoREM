package ch.oliumbi.neorem.repositories;

import ch.oliumbi.neorem.entities.Patient;
import ch.oliumbi.neorem.entities.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {

    Optional<Patient> findByExternalId(String externalId);

    Page<Patient> findAll(Specification<Study> studySpecification, Pageable pageable);
}
