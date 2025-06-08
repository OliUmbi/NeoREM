package ch.oliumbi.neorem.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @Secured("TEST")
    @GetMapping()
    public String all() {
        return "Hello World";
    }
}
