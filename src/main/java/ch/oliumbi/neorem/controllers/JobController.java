package ch.oliumbi.neorem.controllers;

import ch.oliumbi.neorem.entities.Job;
import ch.oliumbi.neorem.services.JobService;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("Job")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @Secured("TEST")
    @GetMapping
    public List<Job> all() {
        return jobService.all();
    }

    // todo get id (only then show executions)
    @Secured("TEST")
    @GetMapping("{id}")
    public Job byId(@RequestParam UUID id) {
        return jobService.byId(id);
    }

    @Secured("TEST")
    @PostMapping
    public void create(@RequestBody CreateRequest createRequest) {
        jobService.create(createRequest.task, createRequest.cron, createRequest.enabled, createRequest.parameters);
    }

    @Secured("TEST")
    @PutMapping("{id}")
    public void update(@RequestParam UUID id, @RequestBody UpdateRequest updateRequest) {
        jobService.update(id, updateRequest.task, updateRequest.cron, updateRequest.enabled, updateRequest.parameters);
    }

    @Secured("TEST")
    @DeleteMapping("{id}")
    public void delete(@RequestParam UUID id) {
        jobService.delete(id);
    }

    @Data
    public static class CreateRequest {

        @NotNull
        private String task;

        @NotNull
        private String cron;

        @NotNull
        private Boolean enabled;

        private Map<String, String> parameters;
    }

    @Data
    public static class UpdateRequest {

        @NotNull
        private String task;

        @NotNull
        private String cron;

        @NotNull
        private Boolean enabled;

        private Map<String, String> parameters;
    }
}
