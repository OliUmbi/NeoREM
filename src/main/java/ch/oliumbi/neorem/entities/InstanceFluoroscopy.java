package ch.oliumbi.neorem.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstanceFluoroscopy {

    private UUID instancesId;
    private String type;
    private Double pulseRate;
    private String plane;
    private Double doseAreaProduct;
    private Double doseReferencePoint;
    private Double duration;
    private Double exposureTime;
    private Double voltagePeak;
    private Double tubeCurrent;
    private Double anglePrimary;
    private Double angleSecondary;
    private Double detectorSize;
    private Double sourceDetectorDistance;
    private String orientation;

    private Set<InstanceFluoroscopyFilter> filters = new HashSet<>();
}
