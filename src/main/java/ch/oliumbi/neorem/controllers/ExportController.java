package ch.oliumbi.neorem.controllers;

import ch.oliumbi.neorem.services.ExecutionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Export")
public class ExportController {

    private final ExecutionService executionService;

    public ExportController(ExecutionService executionService) {
        this.executionService = executionService;
    }

    // todo get all

    // todo delete (not sure if needed)
}
