package ch.oliumbi.neorem.controllers;

import ch.oliumbi.neorem.services.AuthenticationService;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("authenticate")
    public String authenticate(@RequestBody AuthenticateRequest authenticateRequest) {
        return authenticationService.authenticate(authenticateRequest.name, authenticateRequest.password);
    }

    @Data
    public static class AuthenticateRequest {

        @NotNull
        private String name;

        @NotNull
        private String password;
    }
}
