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
public class InstanceRadiography {

    private UUID instancesId;
    private Integer anatomicalStructure;
    private Double laterality;
    private Double targetRegion;
    private Double imageView;
    private Double voltagePeak;
    private Double tubeCurrent;
    private Double exposureTime;
    private Double sourceImageDistance;
    private Double gridFocalDistance;
    private Double exposureIndex;
    private Double targetExposureIndex;
    private Double deviationIndex;
    private Double relativeXrayExposure;
    private Double relativeExposureUnit;
    private Double doseAreaProduct;
    private Double exposureControlMode;

    private Set<InstanceRadiographyFilter> filters = new HashSet<>();
}
