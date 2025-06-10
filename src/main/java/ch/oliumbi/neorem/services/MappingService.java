package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.repositories.JobRepository;
import ch.oliumbi.neorem.repositories.MappingRepository;
import org.springframework.stereotype.Service;

@Service
public class MappingService {

    private final MappingRepository mappingRepository;

    public MappingService(MappingRepository mappingRepository) {
        this.mappingRepository = mappingRepository;
    }
}
