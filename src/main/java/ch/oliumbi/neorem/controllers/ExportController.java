package ch.oliumbi.neorem.controllers;

import ch.oliumbi.neorem.entities.Export;
import ch.oliumbi.neorem.services.ExportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("Export")
public class ExportController {

    private final ExportService exportService;

    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @Secured("TEST")
    @GetMapping
    public Page<Export> all(
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String filters,
            @PageableDefault(sort = "datetime") Pageable pageable) {
        return exportService.all(pageable, from, to, type, filters);
    }

    @Secured("TEST")
    @GetMapping("{id}")
    public Export byId(@PathVariable UUID id) {
        return exportService.byId(id);
    }

    // todo delete (not sure if needed)

    // todo start export with params for studies, patients, devices and diagrams
}
