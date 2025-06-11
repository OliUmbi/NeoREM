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
@Table(name = "events")
public class Event {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "study_id")
    private UUID studyId;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "modality")
    private String modality;

    @Column(name = "comment")
    private String comment;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "protocol")
    private String protocol;

    @Column(name = "type")
    private String type;

    @Column(name = "plane")
    private String plane;

    @Column(name = "region")
    private String region;

    @Column(name = "anatomy")
    private String anatomy;

    @Column(name = "laterality")
    private String laterality;

    @Column(name = "view")
    private String view;

    @Column(name = "orientation")
    private String orientation;

    @Column(name = "exposure_time")
    private Double exposureTime;

    @Column(name = "exposure_time_rotation")
    private Double exposureTimeRotation;

    @Column(name = "duration")
    private Double duration;

    @Column(name = "scanning_length")
    private Double scanningLength;

    @Column(name = "pulse_rate")
    private Double pulseRate;

    @Column(name = "pulse_time")
    private Double pulseTime;

    @Column(name = "voltage_peak")
    private Double voltagePeak;

    @Column(name = "tube_current")
    private Double tubeCurrent;

    // todo review if needed and if so maybe add to the mean tube current
    @Column(name = "tube_current_peak")
    private Double tubeCurrentPeak;

    @Column(name = "tube_current_time")
    private Double tubeCurrentTime;

    @Column(name = "dose_area_product")
    private Double doseAreaProduct;

    @Column(name = "dose_length_product")
    private Double doseLengthProduct;

    @Column(name = "dose_index_volume")
    private Double doseIndexVolume;

    @Column(name = "dose_reference_point")
    private Double doseReferencePoint;

    @Column(name = "dose_organ")
    private Double doseOrgan;

    @Column(name = "modulation")
    private String modulation;

    @Column(name = "compression_thickness")
    private Double compressionThickness;

    @Column(name = "material_target")
    private String materialTarget;

    @Column(name = "material_filter")
    private String materialFilter;

    public Event merge(Event other) {
        if (other == null) {
            return this;
        }

        if (other.externalId != null) externalId = other.externalId;
        if (other.modality != null) modality = other.modality;
        if (other.comment != null) comment = other.comment;
        if (other.date != null) date = other.date;
        if (other.time != null) time = other.time;
        if (other.protocol != null) protocol = other.protocol;
        if (other.type != null) type = other.type;
        if (other.plane != null) plane = other.plane;
        if (other.region != null) region = other.region;
        if (other.anatomy != null) anatomy = other.anatomy;
        if (other.laterality != null) laterality = other.laterality;
        if (other.view != null) view = other.view;
        if (other.orientation != null) orientation = other.orientation;
        if (other.exposureTime != null) exposureTime = other.exposureTime;
        if (other.exposureTimeRotation != null) exposureTimeRotation = other.exposureTimeRotation;
        if (other.duration != null) duration = other.duration;
        if (other.scanningLength != null) scanningLength = other.scanningLength;
        if (other.pulseRate != null) pulseRate = other.pulseRate;
        if (other.pulseTime != null) pulseTime = other.pulseTime;
        if (other.voltagePeak != null) voltagePeak = other.voltagePeak;
        if (other.tubeCurrent != null) tubeCurrent = other.tubeCurrent;
        if (other.tubeCurrentPeak != null) tubeCurrentPeak = other.tubeCurrentPeak;
        if (other.tubeCurrentTime != null) tubeCurrentTime = other.tubeCurrentTime;
        if (other.doseAreaProduct != null) doseAreaProduct = other.doseAreaProduct;
        if (other.doseLengthProduct != null) doseLengthProduct = other.doseLengthProduct;
        if (other.doseIndexVolume != null) doseIndexVolume = other.doseIndexVolume;
        if (other.doseReferencePoint != null) doseReferencePoint = other.doseReferencePoint;
        if (other.doseOrgan != null) doseOrgan = other.doseOrgan;
        if (other.modulation != null) modulation = other.modulation;
        if (other.compressionThickness != null) compressionThickness = other.compressionThickness;
        if (other.materialTarget != null) materialTarget = other.materialTarget;
        if (other.materialFilter != null) materialFilter = other.materialFilter;

        return this;
    }
}
