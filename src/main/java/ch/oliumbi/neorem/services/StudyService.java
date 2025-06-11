package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.controllers.StudyController;
import ch.oliumbi.neorem.entities.Event;
import ch.oliumbi.neorem.entities.Study;
import ch.oliumbi.neorem.repositories.EventRepository;
import ch.oliumbi.neorem.repositories.StudyRepository;
import ch.oliumbi.neorem.specifications.StudySpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudyService {

    private final StudyRepository studyRepository;
    private final EventRepository eventRepository;

    public StudyService(StudyRepository studyRepository, EventRepository eventRepository) {
        this.studyRepository = studyRepository;
        this.eventRepository = eventRepository;
    }

    public Page<Study> all(Pageable pageable, UUID patientId, UUID deviceId, String accessionId, String modality, LocalDate from, LocalDate to, String description) {
        return studyRepository.findAll(StudySpecification.filterAll(patientId, deviceId, accessionId, modality, from, to, description), pageable);
    }

    public Study byId(UUID id) {
        return studyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void delete(UUID id) {
        studyRepository.deleteById(id);
    }
}
