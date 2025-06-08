package ch.oliumbi.neorem.controllers;

import ch.oliumbi.neorem.entities.Study;
import ch.oliumbi.neorem.services.StudyService;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@RestController
@RequestMapping("study")
public class StudyController {

    private final StudyService studyService;

    public StudyController(StudyService studyService) {
        this.studyService = studyService;
    }

    @Secured("TEST")
    @GetMapping("{id}")
    public Study byId(@RequestParam UUID id) {
        return studyService.byId(id);
    }

    // todo acquisition protocol (inside event), bmi, location, dlp, dap, ...,
    @Secured("TEST")
    @GetMapping
    public Page<Study> all(
            @RequestParam(required = false) String accessionId,
            @RequestParam(required = false) String modality,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to,
            @RequestParam(required = false) String description,
            @PageableDefault(page = 0, size = 10, sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {
        return studyService.all(pageable, accessionId, modality, from, to, description);
    }

    // todo maybe remove
    @Data
    public static class AllResponse {
        private UUID id;
        private UUID patientId;
        private UUID deviceId;
        private String accessionId;
        private String modality;
        private LocalDate date;
        private LocalTime time;
        private String description;
        private String institution;
        private String department;
        private String station;
        private Integer eventsAmount;
        private Double averageGlandularDoseLeft;
        private Double averageGlandularDoseRight;
        private Double durationTotal;
        private Double doseLengthProduct;
        private Double doseAreaProductTotal;
        private Double doseReferencePointTotal;
    }
}
