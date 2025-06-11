package ch.oliumbi.neorem.controllers;

import ch.oliumbi.neorem.entities.Device;
import ch.oliumbi.neorem.entities.Patient;
import ch.oliumbi.neorem.services.DeviceService;
import jakarta.persistence.Column;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("Device")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Secured("TEST")
    @GetMapping
    public Page<Device> all(
            @RequestParam(required = false) String manufacturer,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String serial,
            @RequestParam(required = false) String software,
            @PageableDefault(sort = "manufacturer") Pageable pageable) {
        return deviceService.all(pageable, manufacturer, model, serial, software);
    }

    @Secured("TEST")
    @GetMapping("{id}")
    public Device byId(@RequestParam UUID id) {
        return deviceService.byId(id);
    }

    @Secured("TEST")
    @DeleteMapping("{id}")
    public void delete(@RequestParam() UUID id) {
        deviceService.delete(id);
    }

    // todo diagrams
}
