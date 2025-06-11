package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.entities.Export;
import ch.oliumbi.neorem.repositories.ExportRepository;
import ch.oliumbi.neorem.specifications.ExportSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ExportService {

    private final ExportRepository exportRepository;

    public ExportService(ExportRepository exportRepository) {
        this.exportRepository = exportRepository;
    }

    public Page<Export> all(Pageable pageable, LocalDateTime from, LocalDateTime to, String type, String filters) {
        return exportRepository.findAll(ExportSpecification.filterAll(from, to, type, filters), pageable);
    }

    public Export byId(UUID id) {
        return exportRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
