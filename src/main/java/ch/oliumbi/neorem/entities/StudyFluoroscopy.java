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
public class StudyFluoroscopy {

    private UUID studiesId;
    private Double doseAreaProductFluoroscopy;
    private Double doseAreaProductAcquisition;
    private Double doseAreaProductTotal;
    private Double doseReferencePointFluoroscopy;
    private Double doseReferencePointAcquisition;
    private Double doseReferencePointTotal;
    private Double durationFluoroscopy;
    private Double durationAcquisition;
    private String referencePointDefinition;
}
