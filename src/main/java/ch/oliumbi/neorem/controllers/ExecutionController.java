package ch.oliumbi.neorem.controllers;

import ch.oliumbi.neorem.entities.Execution;
import ch.oliumbi.neorem.services.ExecutionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("Execution")
public class ExecutionController {

    private final ExecutionService executionService;

    public ExecutionController(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @Secured("TEST")
    @GetMapping
    public Page<Execution> all(
            @RequestParam(required = false) UUID jobId,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @RequestParam(required = false) String task,
            @RequestParam(required = false) String status,
            @PageableDefault(sort = "start", direction = Direction.DESC) Pageable pageable) {
        return executionService.all(pageable, jobId, from, to, task, status);
    }
}
