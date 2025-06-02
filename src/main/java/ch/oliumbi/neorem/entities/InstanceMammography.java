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
public class InstanceMammography {

    private UUID instancesId;
    private String laterality;
    private String view;
    private String viewModifier;
    private Double compressionThickness;
    private String target;
    private Double entranceSurfaceDose;
    private Double averageGlandularDose;
    private String exposureControlMode;
    // todo exposureControlModeDescription maybe useful

    private Set<InstanceMammographyFilter> filters = new HashSet<>();
}
