package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.entities.Execution;
import ch.oliumbi.neorem.repositories.ExecutionRepository;
import ch.oliumbi.neorem.repositories.ExportRepository;
import ch.oliumbi.neorem.repositories.JobRepository;
import ch.oliumbi.neorem.specifications.EventSpecification;
import ch.oliumbi.neorem.specifications.ExecutionSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ExecutionService {

    private final ExecutionRepository executionRepository;

    public ExecutionService(ExecutionRepository executionRepository) {
        this.executionRepository = executionRepository;
    }

    public Page<Execution> all(Pageable pageable, UUID jobId, LocalDateTime from, LocalDateTime to, String task, String status) {
        return executionRepository.findAll(ExecutionSpecification.filterAll(jobId, from, to, task, status), pageable);
    }
}
