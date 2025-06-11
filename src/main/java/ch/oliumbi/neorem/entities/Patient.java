package ch.oliumbi.neorem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patients")
public class Patient {

    // todo maybe check if patient infos even exist or if it is just empty

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "external_issuer")
    private String externalIssuer;

    @Column(name = "sex")
    private String sex;

    public Patient merge(Patient other) {
        if (other == null) {
            return this;
        }

        if (other.externalId != null) externalId = other.externalId;
        if (other.externalIssuer != null) externalIssuer = other.externalIssuer;
        if (other.sex != null) sex = other.sex;

        return this;
    }
}
