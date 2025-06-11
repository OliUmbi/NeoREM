package ch.oliumbi.neorem.controllers;

import ch.oliumbi.neorem.entities.Event;
import ch.oliumbi.neorem.entities.Study;
import ch.oliumbi.neorem.services.EventService;
import ch.oliumbi.neorem.services.StudyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("Event")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // todo maybe some more filters like modality or time
    @Secured("TEST")
    @GetMapping
    public Page<Event> all(
            @RequestParam(required = false) UUID studyId,
            @PageableDefault(sort = "time") Pageable pageable) {
        return eventService.all(pageable, studyId);
    }

    // todo maybe a byId and extend it to a full view so it is filterable as well

    @Secured("TEST")
    @DeleteMapping("{id}")
    public void delete(@RequestParam UUID id) {
        eventService.delete(id);
    }

    // todo maybe diagrams
}
