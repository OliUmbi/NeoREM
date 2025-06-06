package ch.oliumbi.neorem.repositories;

import ch.oliumbi.neorem.entities.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudyRepository extends JpaRepository<Study, UUID> {

    Optional<Study> findByExternalId(String externalId);
}
