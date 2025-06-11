package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.entities.Device;
import ch.oliumbi.neorem.repositories.DeviceRepository;
import ch.oliumbi.neorem.repositories.StudyRepository;
import ch.oliumbi.neorem.specifications.DeviceSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Page<Device> all(Pageable pageable, String manufacturer, String model, String serial, String software) {
        return deviceRepository.findAll(DeviceSpecification.filterAll(manufacturer, model, serial, software), pageable);
    }

    public Device byId(UUID id) {
        return deviceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void delete(UUID id) {
        deviceRepository.deleteById(id);
    }
}
