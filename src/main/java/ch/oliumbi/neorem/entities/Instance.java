package ch.oliumbi.neorem.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Instance {

    private UUID id;
    private UUID studiesId;
    private String eventId;
    private String modality;
    private String comment;
    private LocalDate date;
    private LocalTime time;
    private String protocol;
    private String type;
    private String plane;
    private String region;
    private String anatomy;
    private String laterality;
    private String view;
    private String orientation;
    private Double exposureTime;
    private Double exposureTimeRotation;
    private Double duration;
    private Double scanningLength;
    private Double pulseWidth;
    private Double pulseRate;
    private Double voltagePeak;
    private Double tubeCurrent;
    private Double tubeCurrentPeak;
    private Double tubeCurrentTime;
    private Double doseAreaProduct;
    private Double doseLengthProduct;
    private Double doseIndexVolume;
    private Double doseReferencePoint;
    private Double doseOrgan;
    private String modulation;
    private Double compressionThickness;
}
