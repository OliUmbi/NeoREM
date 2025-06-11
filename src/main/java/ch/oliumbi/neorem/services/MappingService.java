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

    // todo figure out how to apply mappings. if it is straight in the database or dynamically by request, or in the frontend
    // there are problems like filtering/sorting/export on the other hand there is ingest/changes/etc.
    // probably directly in the database for performance reasons, very important to update the mappings everytime the mappings or the data changes
}
