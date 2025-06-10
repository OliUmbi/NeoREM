package ch.oliumbi.neorem.entities;

import jakarta.persistence.*;
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
@Entity
@Table(name = "mappings")
public class Mapping {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "modality", nullable = false)
    private String modality;

    @Column(name = "name", nullable = false)
    private String name;

    // todo rethink
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mapping_id", referencedColumnName = "id")
    private Set<MappingProtocol> protocols = new HashSet<>();

    // todo rethink
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mapping_id", referencedColumnName = "id")
    private Set<MappingDescription> descriptions = new HashSet<>();
}
