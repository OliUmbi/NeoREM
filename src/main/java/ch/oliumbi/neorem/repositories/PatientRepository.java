package ch.oliumbi.neorem.repositories;

import ch.oliumbi.neorem.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {

    Optional<Patient> findByExternalId(String externalId);
}
