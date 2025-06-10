package ch.oliumbi.neorem.repositories;

import ch.oliumbi.neorem.entities.Mapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MappingRepository extends JpaRepository<Mapping, UUID> {

}
