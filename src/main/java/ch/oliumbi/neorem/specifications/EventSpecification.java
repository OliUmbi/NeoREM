package ch.oliumbi.neorem.specifications;

import ch.oliumbi.neorem.entities.Event;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventSpecification {

    public static Specification<Event> filterAll(UUID studyId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (studyId != null) {
                predicates.add(criteriaBuilder.equal(root.get("studyId"), studyId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
