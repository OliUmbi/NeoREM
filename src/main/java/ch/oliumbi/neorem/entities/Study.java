package ch.oliumbi.neorem.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String reason;
    private String requestedProcedure;
    private String performedProcedure;
    private String institution;
    private String department;
    private String station;
    private String physicians;
    private String operators;
    private Integer height;
    private Integer weight;
    private Double bodyMassIndex;
    private Integer age;
    private String pregnancy;
    private String comment;

    private Set<Instance> instances;
}
