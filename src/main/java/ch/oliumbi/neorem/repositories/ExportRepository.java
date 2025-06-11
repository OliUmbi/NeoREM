package ch.oliumbi.neorem.repositories;

import ch.oliumbi.neorem.entities.Export;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExportRepository extends JpaRepository<Export, UUID> {

    Page<Export> findAll(Specification<Export> exportSpecification, Pageable pageable);
}
