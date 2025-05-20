package ch.oliumbi.neorem.security;

import ch.oliumbi.neorem.entities.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
public class SecurityJWT {

    // todo move
    private String secret = "aksdjhflkasdjhflaskdjfhlasdkfjhs";
    private long expiration = 3600000;

    private Algorithm algorithm;
    private JWTVerifier verifier;

    @PostConstruct
    public void initialize() {
        // todo check for small secrets

        algorithm = Algorithm.HMAC256(secret);
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
                    .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
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
