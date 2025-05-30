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
public class InstanceFluoroscopyFilter {

    private UUID id;
    private UUID instancesFluoroscopiesId;
    private String material;
    private Double thicknessMin;
    private Double thicknessMax;
}
