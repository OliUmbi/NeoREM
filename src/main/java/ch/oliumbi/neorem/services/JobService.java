package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.entities.Job;
import ch.oliumbi.neorem.entities.Notification;
import ch.oliumbi.neorem.repositories.JobRepository;
import ch.oliumbi.neorem.repositories.SettingRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> all() {
        return jobRepository.findAll();
    }

    public Job byId(UUID id) {
        return jobRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void create(String task, String cron, Boolean enabled) {
        Job job = new Job();
        job.setId(UUID.randomUUID());
        job.setTask(task);
        job.setCron(cron);
        job.setEnabled(enabled);

        jobRepository.save(job);
    }

    public void update(UUID id, String task, String cron, Boolean enabled) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        job.setTask(task);
        job.setCron(cron);
        job.setEnabled(enabled);

        jobRepository.save(job);
    }

    public void delete(UUID id) {
        jobRepository.deleteById(id);
    }
}
