package ch.oliumbi.neorem.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
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
    private Integer events;
    private Double doseLengthProduct;
    private Double doseAreaProduct;
    private Double averageGlandularDoseLeft;
    private Double averageGlandularDoseRight;
    private Double durationTotal;
    private Double durationFluoroscopy;
    private Double durationAcquisition;
    private Double doseAreaProductTotal;
    private Double doseAreaProductFluoroscopy;
    private Double doseAreaProductAcquisition;
    private Double doseReferencePointTotal;
    private Double doseReferencePointFluoroscopy;
    private Double doseReferencePointAcquisition;
    private String pharmaceuticalAgent;
    private String pharmaceuticalRadionuclide;
    private Double pharmaceuticalHalfLife;
    private Double pharmaceuticalActivity;
    private Double pharmaceuticalDose;
    private String pharmaceuticalRoute;
    private String pharmaceuticalLaterality;
    private LocalTime pharmaceuticalTime;
    private String pharmaceuticalComment;

    private Set<Instance> instances = new HashSet<>();
}
