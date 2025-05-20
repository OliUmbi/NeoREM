package ch.oliumbi.neorem.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private JWT jwt;

    @Data
    public static class JWT {
        private String secret;
        private long expiration;
    }
}
