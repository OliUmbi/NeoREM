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
public class InstanceNuclearMedicine {

    private UUID instancesId;
    private String radiopharmaceuticalAgent;
    private String radionuclide;
    private String radionuclideHalfLife;
    private String administeredActivity;
    private String effectiveDose;
    private UUID associatedInstancesId;
    private String associatedProcedure;
    private String radiopharmaceuticalStart;
    private String radiopharmaceuticalStop;
    private String administration;
    private String participant;
    private String participantRole;
    private Double slices;
    private String reconstructionMethod;
    private Double coincidenceWindowWidth;
    private Double energyWindowLower;
    private Double energyWindowUpper;
    private String scanProgression;
    private Double rrIntervals;
    private Double timeSlots;
    private Double timeSlices;
    private String findingSite;
    private String laterality;
    private Double organDose;
    private Double mass;
    private String measurementMethod;
    private String referenceAuthority;
}
