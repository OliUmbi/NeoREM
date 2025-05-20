package ch.oliumbi.neorem.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users_roles")
public class UserRole {

    @EmbeddedId
    private UserRoleId id;
}
