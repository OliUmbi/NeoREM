package ch.oliumbi.neorem.ingest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DicomStructuredReport {

    private String relationship;
    private String type;
    private String name;

    private String value;
    private String floatingPoint;
    private String numerator;
    private String denominator;
    private String unit;

    private List<DicomStructuredReport> children;
}
