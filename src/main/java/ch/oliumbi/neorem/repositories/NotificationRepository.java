package ch.oliumbi.neorem.repositories;

import ch.oliumbi.neorem.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

}
