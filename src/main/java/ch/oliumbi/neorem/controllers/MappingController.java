package ch.oliumbi.neorem.controllers;

import ch.oliumbi.neorem.services.MappingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Mapping")
public class MappingController {

    private final MappingService mappingService;

    public MappingController(MappingService mappingService) {
        this.mappingService = mappingService;
    }

    // todo get all

    // todo get id (only then show executions)

    // todo create (recalculate mappings or something)

    // todo update (recalculate mappings)

    // todo delete (recalculate mappings)
}
