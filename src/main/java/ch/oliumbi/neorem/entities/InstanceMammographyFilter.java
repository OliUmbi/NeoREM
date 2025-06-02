package ch.oliumbi.neorem.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstanceMammographyFilter {

    private UUID id;
    private UUID instancesMammographiesId;
    private String material;
    private Double voltagePeak;
    private Double tubeCurrent;
    private Double exposureTime;
    private Double tubeCurrentTime;
}
