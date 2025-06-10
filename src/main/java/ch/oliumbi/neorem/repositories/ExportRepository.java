package ch.oliumbi.neorem.repositories;

import ch.oliumbi.neorem.entities.Export;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExportRepository extends JpaRepository<Export, UUID> {

}
