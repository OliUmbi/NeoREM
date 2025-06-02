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
public class Device {

    private UUID id;
    private String manufacturer;
    private String model;
    private String serial;
    private String software;

    private Set<Study> studies = new HashSet<>();
}
