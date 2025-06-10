package ch.oliumbi.neorem.repositories;

import ch.oliumbi.neorem.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {

}
