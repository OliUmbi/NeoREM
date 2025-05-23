package ch.oliumbi.neorem.entities;

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
public class Instance {

    private UUID id;
    private UUID studiesId;
    private String modality;
    private String seriesId;
    private String instanceId;
    private String date;
    private String description;
    private String protocol;
    private String comment;

    private Set<Item> items;
}
