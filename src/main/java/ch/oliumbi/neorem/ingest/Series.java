package ch.oliumbi.neorem.ingest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Series {

    private UUID id;
    private String seriesInstanceUID;
    private Integer seriesNumber;
    private LocalDate seriesDate;
    private LocalTime seriesTime;
    private String seriesDescription;
    private String modality;
    private String bodyPartExamined;
    private String protocolName;
    private String manufacturer;
    private String manufacturerModelName;
    private String deviceSerialNumber;
    private String softwareVersions;

    private Set<Instance> instances;
}
