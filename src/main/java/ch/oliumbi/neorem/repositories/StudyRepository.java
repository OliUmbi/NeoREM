package ch.oliumbi.neorem.repositories;

import ch.oliumbi.neorem.entities.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudyRepository extends JpaRepository<Study, UUID> {

    Optional<Study> findByExternalId(String externalId);

    Page<Study> findAll(Specification<Study> studySpecification, Pageable pageable);

    List<Study> findAllByPatientId(UUID patientId);

    List<Study> findAllByDeviceId(UUID deviceId);
}
