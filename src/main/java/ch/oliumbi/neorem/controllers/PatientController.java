package ch.oliumbi.neorem.controllers;

import ch.oliumbi.neorem.entities.Patient;
import ch.oliumbi.neorem.entities.Study;
import ch.oliumbi.neorem.services.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("Patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @Secured("TEST")
    @GetMapping
    public Page<Patient> all(
            @RequestParam(required = false) String externalId,
            @RequestParam(required = false) String externalIssuer,
            @PageableDefault(sort = "externalId") Pageable pageable) {
        return patientService.all(pageable, externalId, externalIssuer);
    }

    @Secured("TEST")
    @GetMapping("{id}")
    public Patient byId(@RequestParam UUID id) {
        return patientService.byId(id);
    }

    @Secured("TEST")
    @DeleteMapping("{id}")
    public void delete(@RequestParam UUID id) {
        patientService.delete(id);
    }

    // todo diagrams
}
