package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.entities.Device;
import ch.oliumbi.neorem.repositories.DeviceRepository;
import ch.oliumbi.neorem.repositories.SettingRepository;
import ch.oliumbi.neorem.repositories.StudyRepository;
import ch.oliumbi.neorem.specifications.DeviceSpecification;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SettingService {

    private final SettingRepository settingRepository;

    public SettingService(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }
}
