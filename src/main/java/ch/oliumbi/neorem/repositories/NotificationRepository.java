package ch.oliumbi.neorem.repositories;

import ch.oliumbi.neorem.entities.Notification;
import ch.oliumbi.neorem.entities.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    Page<Notification> findAll(Specification<Notification> studySpecification, Pageable pageable);
}
