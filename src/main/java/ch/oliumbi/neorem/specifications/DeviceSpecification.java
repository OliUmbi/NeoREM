package ch.oliumbi.neorem.specifications;

import ch.oliumbi.neorem.entities.Device;
import ch.oliumbi.neorem.entities.Study;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DeviceSpecification {

    public static Specification<Device> filterAll(String manufacturer, String model, String serial, String software) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(manufacturer)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("manufacturer")), "%" + manufacturer.toLowerCase() + "%"));
            }

            if (StringUtils.hasText(model)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("model")), "%" + model.toLowerCase() + "%"));
            }

            if (StringUtils.hasText(serial)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("serial")), "%" + serial.toLowerCase() + "%"));
            }

            if (StringUtils.hasText(software)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("software")), "%" + software.toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
