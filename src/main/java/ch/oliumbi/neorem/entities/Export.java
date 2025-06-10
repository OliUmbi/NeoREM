package ch.oliumbi.neorem.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exports")
public class Export {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    // todo one to one connection
    @Column(name = "execution_id", nullable = false)
    private UUID executionId;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "filters", nullable = false)
    private String filters;

    @Column(name = "location")
    private String location;
}
