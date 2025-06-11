package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.entities.Setting;
import ch.oliumbi.neorem.repositories.SettingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SettingService {

    private final SettingRepository settingRepository;

    public SettingService(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    public List<Setting> all() {
        return settingRepository.findAll();
    }

    public void update(String name, String value) {
        Setting setting = settingRepository.findById(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        setting.setValue(value);

        settingRepository.save(setting);
    }
}
