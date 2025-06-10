package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.repositories.JobRepository;
import ch.oliumbi.neorem.repositories.SettingRepository;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
}
