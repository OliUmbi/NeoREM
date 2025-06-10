package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.repositories.ExecutionRepository;
import ch.oliumbi.neorem.repositories.ExportRepository;
import ch.oliumbi.neorem.repositories.JobRepository;
import org.springframework.stereotype.Service;

@Service
public class ExecutionService {

    private final ExecutionRepository executionRepository;

    public ExecutionService(ExecutionRepository executionRepository) {
        this.executionRepository = executionRepository;
    }
}
