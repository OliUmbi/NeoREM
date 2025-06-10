package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.repositories.ImportRepository;
import ch.oliumbi.neorem.repositories.JobRepository;
import org.springframework.stereotype.Service;

@Service
public class ImportService {

    private final ImportRepository importRepository;

    public ImportService(ImportRepository importRepository) {
        this.importRepository = importRepository;
    }
}
