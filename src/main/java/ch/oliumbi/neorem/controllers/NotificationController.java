package ch.oliumbi.neorem.controllers;

import ch.oliumbi.neorem.entities.Notification;
import ch.oliumbi.neorem.entities.Patient;
import ch.oliumbi.neorem.services.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("Notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Secured("TEST")
    @GetMapping
    public Page<Notification> all(
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String recipient,
            @RequestParam(required = false) String subject,
            @PageableDefault(sort = "datetime", direction = Direction.DESC) Pageable pageable) {
        return notificationService.all(pageable, from, to, status, recipient, subject);
    }
}
