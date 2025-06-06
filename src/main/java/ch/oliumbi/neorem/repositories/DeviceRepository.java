package ch.oliumbi.neorem.repositories;

import ch.oliumbi.neorem.entities.Device;
import ch.oliumbi.neorem.entities.User;
import jakarta.persistence.Column;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {

    Optional<Device> findByManufacturerAndModelAndSerialAndSoftware(String manufacturer, String model, String serial, String software);
}
