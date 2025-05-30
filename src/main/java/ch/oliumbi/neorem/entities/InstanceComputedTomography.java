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
public class InstanceComputedTomography {

    private UUID instancesId;
    private String type;
    private Double doseIndexVolume;
    private Double doseLengthProduct;
    private Double scanningLength;
    private Double voltagePeak;
    private Double tubeCurrent;
    private Double tubeCurrentMax;
    private Double exposureTimeRotation;
    private Double pitch;
    private Double exposureTime;
    private Double sliceThickness;
    private Double collimation;
    private String modulation;
}
