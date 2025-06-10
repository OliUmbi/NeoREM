package ch.oliumbi.neorem.repositories;

import ch.oliumbi.neorem.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    Optional<Event> findByExternalId(String externalId);

    List<Event> findAllByStudyId(UUID studyId);
}
