package ch.oliumbi.neorem.controllers;

import ch.oliumbi.neorem.services.AuthenticationService;
import lombok.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("authenticate")
    public String authenticate(AuthenticateRequest authenticateRequest) {
        return authenticationService.authenticate(authenticateRequest.name, authenticateRequest.password);
    }

    @Data
    public static class AuthenticateRequest {
        private String name;
        private String password;
    }
}
