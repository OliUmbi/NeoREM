package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.entities.Device;
import ch.oliumbi.neorem.entities.Patient;
import ch.oliumbi.neorem.repositories.DeviceRepository;
import ch.oliumbi.neorem.repositories.PatientRepository;
import ch.oliumbi.neorem.repositories.StudyRepository;
import ch.oliumbi.neorem.specifications.DeviceSpecification;
import ch.oliumbi.neorem.specifications.PatientSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final StudyRepository studyRepository;

    public DeviceService(DeviceRepository deviceRepository, StudyRepository studyRepository) {
        this.deviceRepository = deviceRepository;
        this.studyRepository = studyRepository;
    }

    public Device byId(UUID id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        device.setStudies(studyRepository.findAllByDeviceId(device.getId()));

        return device;
    }

    public Page<Device> all(Pageable pageable, String manufacturer, String model, String serial, String software) {
        return deviceRepository.findAll(DeviceSpecification.filterAll(manufacturer, model, serial, software), pageable);
    }
}
