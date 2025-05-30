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
public class InstanceRadiographyFilter {

    private UUID id;
    private UUID instancesRadiographiesId;
    private String material;
    private Double thicknessMin;
    private Double thicknessMax;
}
