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
public class Study {
    private UUID id;
    private String patientAge;
    private Integer pregnancyStatus;
    private String studyInstanceUID;
    private String accessionNumber;
    private LocalDate studyDate;
    private LocalTime studyTime;
    private String studyDescription;
    private String referringPhysicianName;
    private String requestingPhysician;
    private String institutionName;
    private String institutionalDepartmentName;
    private String stationName;

    private Set<Series> series;
}
