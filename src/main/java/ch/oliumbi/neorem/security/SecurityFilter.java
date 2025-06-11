package ch.oliumbi.neorem.security;

import ch.oliumbi.neorem.entities.User;
import ch.oliumbi.neorem.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final SecurityJWT securityJWT;
    private final UserService userService;

    public SecurityFilter(SecurityJWT securityJWT, UserService userService) {
        this.securityJWT = securityJWT;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7);

        // todo this should probably move to authentication service

        Optional<UUID> subject = securityJWT.subject(token);
        if (subject.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        User user = userService.byId(subject.get());
        if (!securityJWT.valid(token, user)) {
            filterChain.doFilter(request, response);
            return;
        }

        SecurityToken securityToken = new SecurityToken(user, token);
        securityToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(securityToken);

        filterChain.doFilter(request, response);
    }
}
