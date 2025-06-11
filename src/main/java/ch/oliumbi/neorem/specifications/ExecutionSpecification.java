package ch.oliumbi.neorem.specifications;

import ch.oliumbi.neorem.entities.Execution;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExecutionSpecification {

    public static Specification<Execution> filterAll(UUID jobId, LocalDateTime from, LocalDateTime to, String task, String status) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (jobId != null) {
                predicates.add(criteriaBuilder.equal(root.get("jobId"), jobId));
            }

            if (from != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("start"), from));
            }

            if (to != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("end"), to));
            }

            if (StringUtils.hasText(task)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("task")), "%" + task.toLowerCase() + "%"));
            }

            if (StringUtils.hasText(status)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + status.toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
