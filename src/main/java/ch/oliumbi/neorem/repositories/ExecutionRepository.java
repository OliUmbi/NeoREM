package ch.oliumbi.neorem.repositories;

import ch.oliumbi.neorem.entities.Execution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExecutionRepository extends JpaRepository<Execution, UUID> {

    Page<Execution> findAll(Specification<Execution> executionSpecification, Pageable pageable);
}
