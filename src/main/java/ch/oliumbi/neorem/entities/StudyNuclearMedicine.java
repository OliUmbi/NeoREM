package ch.oliumbi.neorem.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudyNuclearMedicine {

    private UUID studiesId;
    private Double effectiveDose;
    private String agent;
    private String radionuclide;
    private Double halfLife;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private Double activity;
    private String route;
    private String laterality;
}
