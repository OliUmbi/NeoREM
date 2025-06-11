package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.entities.Event;
import ch.oliumbi.neorem.entities.Study;
import ch.oliumbi.neorem.repositories.EventRepository;
import ch.oliumbi.neorem.repositories.StudyRepository;
import ch.oliumbi.neorem.specifications.EventSpecification;
import ch.oliumbi.neorem.specifications.StudySpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Page<Event> all(Pageable pageable, UUID studyId) {
        return eventRepository.findAll(EventSpecification.filterAll(studyId), pageable);
    }

    public void delete(UUID id) {
        eventRepository.deleteById(id);
    }
}
