package ch.oliumbi.neorem.specifications;

import ch.oliumbi.neorem.entities.Execution;
import ch.oliumbi.neorem.entities.Export;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExportSpecification {

    public static Specification<Export> filterAll(LocalDateTime from, LocalDateTime to, String type, String filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (from != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("datetime"), from));
            }

            if (to != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("datetime"), to));
            }

            if (StringUtils.hasText(type)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("type")), "%" + type.toLowerCase() + "%"));
            }

            if (StringUtils.hasText(filters)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("filters")), "%" + filters.toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
