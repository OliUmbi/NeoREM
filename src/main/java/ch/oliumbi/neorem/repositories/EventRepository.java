package ch.oliumbi.neorem.repositories;

import ch.oliumbi.neorem.entities.Event;
import ch.oliumbi.neorem.entities.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    Optional<Event> findByExternalId(String externalId);

    Page<Event> findAll(Specification<Event> studySpecification, Pageable pageable);
}
