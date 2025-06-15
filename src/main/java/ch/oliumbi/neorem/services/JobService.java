package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.entities.Job;
import ch.oliumbi.neorem.entities.JobParameter;
import ch.oliumbi.neorem.entities.UserRole;
import ch.oliumbi.neorem.entities.UserRoleId;
import ch.oliumbi.neorem.repositories.JobRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
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

    public void create(String task, String cron, Boolean enabled, Map<String, String> parameters) {
        Job job = new Job();
        job.setId(UUID.randomUUID());
        job.setTask(task);
        job.setCron(cron);
        job.setEnabled(enabled);

        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            JobParameter jobParameter = new JobParameter(UUID.randomUUID(), job.getId(), parameter.getKey(), parameter.getValue());
            job.getParameters().add(jobParameter);
        }

        jobRepository.save(job);
    }

    public void update(UUID id, String task, String cron, Boolean enabled, Map<String, String> parameters) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        job.setTask(task);
        job.setCron(cron);
        job.setEnabled(enabled);

        job.getParameters().clear();
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            JobParameter jobParameter = new JobParameter(UUID.randomUUID(), job.getId(), parameter.getKey(), parameter.getValue());
            job.getParameters().add(jobParameter);
        }

        jobRepository.save(job);
    }

    public void delete(UUID id) {
        jobRepository.deleteById(id);
    }
}
