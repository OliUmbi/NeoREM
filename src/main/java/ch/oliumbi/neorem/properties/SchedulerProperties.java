package ch.oliumbi.neorem.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "scheduler")
public class SchedulerProperties {
    private int threadPool;
}
