package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.entities.Notification;
import ch.oliumbi.neorem.repositories.NotificationRepository;
import ch.oliumbi.neorem.specifications.NotificationSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Page<Notification> all(Pageable pageable, LocalDateTime from, LocalDateTime to, String status, String recipient, String subject) {
        return notificationRepository.findAll(NotificationSpecification.filterAll(from, to, status, recipient, subject), pageable);
    }
}
