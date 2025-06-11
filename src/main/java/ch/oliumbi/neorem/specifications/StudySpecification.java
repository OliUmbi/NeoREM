package ch.oliumbi.neorem.specifications;

import ch.oliumbi.neorem.controllers.StudyController;
import ch.oliumbi.neorem.entities.Study;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StudySpecification {

    public static Specification<Study> filterAll(UUID patientId, UUID deviceId, String accessionId, String modality, LocalDate from, LocalDate to, String description) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (patientId != null) {
                predicates.add(criteriaBuilder.equal(root.get("patientId"), patientId));
            }

            if (deviceId != null) {
                predicates.add(criteriaBuilder.equal(root.get("deviceId"), deviceId));
            }

            if (StringUtils.hasText(accessionId)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("accessionId")), "%" + accessionId.toLowerCase() + "%"));
            }

            if (StringUtils.hasText(modality)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("modality")), "%" + modality.toLowerCase() + "%"));
            }

            if (from != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), from));
            }

            if (to != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), to));
            }

            if (StringUtils.hasText(description)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
