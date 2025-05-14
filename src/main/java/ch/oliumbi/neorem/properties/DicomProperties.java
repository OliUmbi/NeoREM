package ch.oliumbi.neorem.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "dicom")
public class DicomProperties {
    private Local local;
    private Remote remote;
    private Output output;

    @Data
    public static class Local {
        private String aet;
        private int port;
    }

    @Data
    public static class Remote {
        private String aet;
        private String host;
        private int port;
    }

    @Data
    public static class Output {
        private String location;
    }
}
