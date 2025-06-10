package ch.oliumbi.neorem.repositories;

import ch.oliumbi.neorem.entities.Import;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImportRepository extends JpaRepository<Import, UUID> {

}
