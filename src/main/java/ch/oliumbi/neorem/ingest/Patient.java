package ch.oliumbi.neorem.ingest;

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
public class Patient {

    private UUID id;
    private String patientID;
    private String issuerOfPatientID;
    private String patientSex;

    private Set<Study> studies;
}
