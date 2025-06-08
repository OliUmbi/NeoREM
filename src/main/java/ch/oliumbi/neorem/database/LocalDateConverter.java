package ch.oliumbi.neorem.database;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDate;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, String> {

    @Override
    public String convertToDatabaseColumn(LocalDate localDate) {
        return localDate != null ? localDate.toString() : null;
    }

    @Override
    public LocalDate convertToEntityAttribute(String string) {
        return string != null ? LocalDate.parse(string) : null;
    }
}
