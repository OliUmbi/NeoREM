package ch.oliumbi.neorem.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    private UUID id;
    private String externalId;
    private String externalIssuer;
    private String sex;

    private Set<Study> studies = new HashSet<>();
}
