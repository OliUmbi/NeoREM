package ch.oliumbi.neorem.specifications;

import ch.oliumbi.neorem.entities.Notification;
import ch.oliumbi.neorem.entities.Study;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationSpecification {

    public static Specification<Notification> filterAll(LocalDateTime from, LocalDateTime to, String status, String recipient, String subject) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (from != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), from));
            }

            if (to != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), to));
            }

            if (StringUtils.hasText(status)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + status.toLowerCase() + "%"));
            }

            if (StringUtils.hasText(recipient)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("recipient")), "%" + recipient.toLowerCase() + "%"));
            }

            if (StringUtils.hasText(subject)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("subject")), "%" + subject.toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
