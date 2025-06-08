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
@Table(name = "devices", uniqueConstraints = @UniqueConstraint(columnNames = {"manufacturer", "model", "serial", "software"}))
public class Device {

    // todo maybe check if device infos even exist or if it is just empty

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "model")
    private String model;

    @Column(name = "serial")
    private String serial;

    @Column(name = "software")
    private String software;

    public Device merge(Device other) {
        if (other == null) {
            return this;
        }

        if (other.manufacturer != null) manufacturer = other.manufacturer;
        if (other.model != null) model = other.model;
        if (other.serial != null) serial = other.serial;
        if (other.software != null) software = other.software;

        return this;
    }
}
