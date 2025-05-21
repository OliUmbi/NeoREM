package ch.oliumbi.neorem.ingest;

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
public class Item {

    private UUID id;
    private String relationshipType;
    private String valueType;
    private String conceptName;
    private String conceptValue;
    private String textValue;
    private Double numericValue;
    private String numericUnit;
    private String uidRefValue;

    private Set<Item> items;
}
