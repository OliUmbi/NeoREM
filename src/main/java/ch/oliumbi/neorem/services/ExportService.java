package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.repositories.ExportRepository;
import ch.oliumbi.neorem.repositories.JobRepository;
import org.springframework.stereotype.Service;

@Service
public class ExportService {

    private final ExportRepository exportRepository;

    public ExportService(ExportRepository exportRepository) {
        this.exportRepository = exportRepository;
    }
}
