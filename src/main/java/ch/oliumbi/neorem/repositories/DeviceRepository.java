package ch.oliumbi.neorem.repositories;

import ch.oliumbi.neorem.entities.Device;
import ch.oliumbi.neorem.entities.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {

    Optional<Device> findByManufacturerAndModelAndSerialAndSoftware(String manufacturer, String model, String serial, String software);

    Page<Device> findAll(Specification<Study> studySpecification, Pageable pageable);
}
