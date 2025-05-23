package ch.oliumbi.neorem.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Study {

    private UUID id;
    private UUID patientsId;
    private UUID devicesId;
    private String modality;
    private String instanceId;
    private String accessionId;
    private String date;
    private String description;
    private String reason;
    private String requestedProcedure;
    private String performedProcedure;
    private String institution;
    private String department;
    private String station;
    private String physicians;
    private String operators;
    private String height;
    private String weight;
    private String bodyMassIndex;
    private String age;
    private String pregnancy;
    private String events;
    private String comment;

    private Set<Instance> instances;
}
