package ch.oliumbi.neorem.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "studies")
public class Study {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "patient_id")
    private UUID patientId;

    @Column(name = "device_id")
    private UUID deviceId;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "accession_id")
    private String accessionId;

    @Column(name = "modality")
    private String modality;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "description")
    private String description;

    @Column(name = "reason")
    private String reason;

    @Column(name = "procedure_requested")
    private String procedureRequested;

    @Column(name = "procedure_performed")
    private String procedurePerformed;

    @Column(name = "institution")
    private String institution;

    @Column(name = "department")
    private String department;

    @Column(name = "station")
    private String station;

    @Column(name = "physicians")
    private String physicians;

    @Column(name = "operators")
    private String operators;

    @Column(name = "height")
    private Integer height;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "body_mass_index")
    private Double bodyMassIndex;

    @Column(name = "age")
    private Integer age;

    @Column(name = "pregnancy")
    private String pregnancy;

    @Column(name = "comment")
    private String comment;

    @Column(name = "events_amount")
    private Integer eventsAmount;

    @Column(name = "average_glandular_dose_left")
    private Double averageGlandularDoseLeft;

    @Column(name = "average_glandular_dose_right")
    private Double averageGlandularDoseRight;

    @Column(name = "duration_total")
    private Double durationTotal;

    @Column(name = "duration_fluoroscopy")
    private Double durationFluoroscopy;

    @Column(name = "duration_acquisition")
    private Double durationAcquisition;

    @Column(name = "dose_length_product")
    private Double doseLengthProduct;

    @Column(name = "dose_area_product_total")
    private Double doseAreaProductTotal;

    @Column(name = "dose_area_product_fluoroscopy")
    private Double doseAreaProductFluoroscopy;

    @Column(name = "dose_area_product_acquisition")
    private Double doseAreaProductAcquisition;

    @Column(name = "dose_reference_point_total")
    private Double doseReferencePointTotal;

    @Column(name = "dose_reference_point_fluoroscopy")
    private Double doseReferencePointFluoroscopy;

    @Column(name = "dose_reference_point_acquisition")
    private Double doseReferencePointAcquisition;

    @Column(name = "pharmaceutical_agent")
    private String pharmaceuticalAgent;

    @Column(name = "pharmaceutical_radionuclide")
    private String pharmaceuticalRadionuclide;

    @Column(name = "pharmaceutical_half_life")
    private Double pharmaceuticalHalfLife;

    @Column(name = "pharmaceutical_activity")
    private Double pharmaceuticalActivity;

    @Column(name = "pharmaceutical_dose")
    private Double pharmaceuticalDose;

    @Column(name = "pharmaceutical_route")
    private String pharmaceuticalRoute;

    @Column(name = "pharmaceutical_laterality")
    private String pharmaceuticalLaterality;

    @Column(name = "pharmaceutical_time")
    private LocalTime pharmaceuticalTime;

    @Column(name = "pharmaceutical_comment")
    private String pharmaceuticalComment;

    public Study merge(Study other) {
        if (other == null) {
            return this;
        }

        if (other.externalId != null) externalId = other.externalId;
        if (other.accessionId != null) accessionId = other.accessionId;
        if (other.modality != null) modality = other.modality;
        if (other.date != null) date = other.date;
        if (other.time != null) time = other.time;
        if (other.description != null) description = other.description;
        if (other.reason != null) reason = other.reason;
        if (other.procedureRequested != null) procedureRequested = other.procedureRequested;
        if (other.procedurePerformed != null) procedurePerformed = other.procedurePerformed;
        if (other.institution != null) institution = other.institution;
        if (other.department != null) department = other.department;
        if (other.station != null) station = other.station;
        if (other.physicians != null) physicians = other.physicians;
        if (other.operators != null) operators = other.operators;
        if (other.height != null) height = other.height;
        if (other.weight != null) weight = other.weight;
        if (other.bodyMassIndex != null) bodyMassIndex = other.bodyMassIndex;
        if (other.age != null) age = other.age;
        if (other.pregnancy != null) pregnancy = other.pregnancy;
        if (other.comment != null) comment = other.comment;
        if (other.eventsAmount != null) eventsAmount = other.eventsAmount;
        if (other.doseLengthProduct != null) doseLengthProduct = other.doseLengthProduct;
        if (other.averageGlandularDoseLeft != null) averageGlandularDoseLeft = other.averageGlandularDoseLeft;
        if (other.averageGlandularDoseRight != null) averageGlandularDoseRight = other.averageGlandularDoseRight;
        if (other.durationTotal != null) durationTotal = other.durationTotal;
        if (other.durationFluoroscopy != null) durationFluoroscopy = other.durationFluoroscopy;
        if (other.durationAcquisition != null) durationAcquisition = other.durationAcquisition;
        if (other.doseAreaProductTotal != null) doseAreaProductTotal = other.doseAreaProductTotal;
        if (other.doseAreaProductFluoroscopy != null) doseAreaProductFluoroscopy = other.doseAreaProductFluoroscopy;
        if (other.doseAreaProductAcquisition != null) doseAreaProductAcquisition = other.doseAreaProductAcquisition;
        if (other.doseReferencePointTotal != null) doseReferencePointTotal = other.doseReferencePointTotal;
        if (other.doseReferencePointFluoroscopy != null) doseReferencePointFluoroscopy = other.doseReferencePointFluoroscopy;
        if (other.doseReferencePointAcquisition != null) doseReferencePointAcquisition = other.doseReferencePointAcquisition;

        return this;
    }

    public UUID getId() {
        return id;
    }
}
