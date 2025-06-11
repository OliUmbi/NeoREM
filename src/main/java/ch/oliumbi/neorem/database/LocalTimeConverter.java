package ch.oliumbi.neorem.database;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalTime;

@Converter(autoApply = true)
public class LocalTimeConverter implements AttributeConverter<LocalTime, String> {

    @Override
    public String convertToDatabaseColumn(LocalTime localTime) {
        return localTime != null ? localTime.toString() : null;
    }

    @Override
    public LocalTime convertToEntityAttribute(String string) {
        return string != null ? LocalTime.parse(string) : null;
    }
}
