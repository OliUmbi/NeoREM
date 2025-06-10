package ch.oliumbi.neorem.security;

import ch.oliumbi.neorem.entities.User;
import ch.oliumbi.neorem.properties.SecurityProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
public class SecurityJWT {

    private final SecurityProperties securityProperties;

    private Algorithm algorithm;
    private JWTVerifier verifier;

    public SecurityJWT(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @PostConstruct
    public void initialize() {
        algorithm = Algorithm.HMAC256(securityProperties.getJwt().getSecret());
        verifier = JWT.require(algorithm).build();
    }

    public Optional<String> generate(User user) {
        try {
            String[] roles = user.getRoles().stream()
                    .map(userRole -> userRole.getId().getRole())
                    .toArray(String[]::new);

            String token = JWT.create()
                    .withSubject(user.getId().toString())
                    .withIssuedAt(new Date())
                    .withExpiresAt(Instant.from(LocalDateTime.now().plusHours(securityProperties.getJwt().getExpiration())))
                    .withClaim("name", user.getName())
                    .withArrayClaim("roles", roles)
                    .sign(algorithm);

            return Optional.of(token);
        } catch (JWTCreationException e) {
            return Optional.empty();
        }
    }

    public boolean valid(String token, User user) {
        return subject(token)
                .map(uuid -> uuid.equals(user.getId()))
                .orElse(false);
    }

    public Optional<UUID> subject(String token) {
        try {
            DecodedJWT jwt = verifier.verify(token);
            UUID id = UUID.fromString(jwt.getSubject());

            return Optional.of(id);
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }
}
