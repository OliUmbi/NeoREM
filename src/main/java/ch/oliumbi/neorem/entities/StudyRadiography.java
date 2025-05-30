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
public class StudyRadiography {

    private UUID studiesId;
    private Integer events;
    private Double doseAreaProduct;
    private Double doseReferencePointTotal;
    private String referencePointDefinition;
}
