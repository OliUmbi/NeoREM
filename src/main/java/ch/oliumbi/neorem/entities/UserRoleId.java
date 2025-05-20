package ch.oliumbi.neorem.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class UserRoleId implements Serializable {

    @Column(name = "users_id", nullable = false, columnDefinition = "BLOB")
    private UUID userId;

    @Column(name = "role", nullable = false)
    private String role;
}
