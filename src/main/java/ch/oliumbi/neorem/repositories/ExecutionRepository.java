package ch.oliumbi.neorem.repositories;

import ch.oliumbi.neorem.entities.Execution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExecutionRepository extends JpaRepository<Execution, UUID> {

}
