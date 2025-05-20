package ch.oliumbi.neorem.security;

import ch.oliumbi.neorem.entities.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SecurityToken extends AbstractAuthenticationToken {

    private final User user;
    private final String token;

    public SecurityToken(User user, String token) {
        super(user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getId().getRole()))
                .toList());

        this.user = user;
        this.token = token;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }
}
