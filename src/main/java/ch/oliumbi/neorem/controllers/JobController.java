package ch.oliumbi.neorem.controllers;

import ch.oliumbi.neorem.services.JobService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Job")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // todo get all

    // todo get id (only then show executions)

    // todo create

    // todo update

    // todo delete
}
