package ch.oliumbi.neorem.controllers;

import ch.oliumbi.neorem.entities.Setting;
import ch.oliumbi.neorem.services.SettingService;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Setting")
public class SettingController {

    private final SettingService settingService;

    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    // todo figure out what roles should exist
    @Secured("TEST")
    @GetMapping
    public List<Setting> all() {
        return settingService.all();
    }

    @Secured("TEST")
    @PutMapping("{name}")
    public void update(@PathVariable String name, @RequestBody UpdateRequest updateRequest) {
        settingService.update(name, updateRequest.value);
    }

    @Data
    public static class UpdateRequest {

        @NotNull
        private String value;
    }
}
