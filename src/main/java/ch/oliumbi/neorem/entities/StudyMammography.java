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
public class StudyMammography {

    private UUID studiesId;
    private Double averageGlandularDoseLeft;
    private Double averageGlandularDoseRight;
}
