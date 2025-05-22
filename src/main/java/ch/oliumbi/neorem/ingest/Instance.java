package ch.oliumbi.neorem.ingest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Instance {

    private UUID id;
    private String sopClassUID;
    private String sopInstanceUID;
    private int instanceNumber;
    private String contentDate;
    private String contentTime;
    private String modality;
    private String seriesDescription;

    private Set<Item> items;
}
