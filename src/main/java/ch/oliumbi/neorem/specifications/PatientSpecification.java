package ch.oliumbi.neorem.specifications;

import ch.oliumbi.neorem.entities.Patient;
import ch.oliumbi.neorem.entities.Study;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientSpecification {

    public static Specification<Patient> filterAll(String externalId, String externalIssuer) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(externalId)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("externalId")), "%" + externalId.toLowerCase() + "%"));
            }

            if (StringUtils.hasText(externalIssuer)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("externalIssuer")), "%" + externalIssuer.toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
