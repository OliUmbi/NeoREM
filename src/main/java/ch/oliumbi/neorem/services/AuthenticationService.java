package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.entities.User;
import ch.oliumbi.neorem.repositories.UserRepository;
import ch.oliumbi.neorem.security.SecurityJWT;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityJWT securityJWT;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, SecurityJWT securityJWT) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.securityJWT = securityJWT;
    }

    public String authenticate(String name, String password) {
        User user = userRepository.findByName(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return securityJWT.generate(user).orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
