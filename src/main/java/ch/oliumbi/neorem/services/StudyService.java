package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.controllers.StudyController;
import ch.oliumbi.neorem.entities.Study;
import ch.oliumbi.neorem.repositories.StudyRepository;
import ch.oliumbi.neorem.specifications.StudySpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudyService {

    private final StudyRepository studyRepository;

    public StudyService(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    public Study byId(UUID id) {
        Optional<Study> study = studyRepository.findById(id);

        if (study.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return study.get();
    }

    public Page<Study> all(Pageable pageable, String accessionId, String modality, LocalDate from, LocalDate to, String description) {
        return studyRepository.findAll(StudySpecification.filterAll(accessionId, modality, from, to, description), pageable);
    }
}
