package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.repositories.JobRepository;
import ch.oliumbi.neorem.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
}
