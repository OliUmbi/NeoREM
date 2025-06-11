package ch.oliumbi.neorem.controllers;

import ch.oliumbi.neorem.entities.Study;
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
@RequestMapping("Study")
public class StudyController {

    private final StudyService studyService;

    public StudyController(StudyService studyService) {
        this.studyService = studyService;
    }

    // todo acquisition protocol (inside event), bmi, location, dlp, dap, ...,
    @Secured("TEST")
    @GetMapping
    public Page<Study> all(
            @RequestParam(required = false) UUID patientId,
            @RequestParam(required = false) UUID deviceId,
            @RequestParam(required = false) String accessionId,
            @RequestParam(required = false) String modality,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to,
            @RequestParam(required = false) String description,
            @PageableDefault(sort = "date", direction = Direction.DESC) Pageable pageable) {
        return studyService.all(pageable, patientId, deviceId, accessionId, modality, from, to, description);
    }

    @Secured("TEST")
    @GetMapping("{id}")
    public Study byId(@RequestParam UUID id) {
        return studyService.byId(id);
    }

    @Secured("TEST")
    @DeleteMapping("{id}")
    public void delete(@RequestParam UUID id) {
        studyService.delete(id);
    }

    // todo diagrams
}
