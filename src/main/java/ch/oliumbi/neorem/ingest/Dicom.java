package ch.oliumbi.neorem.ingest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Dicom extends ArrayList<Dicom> {

    private String name;
    private String value;

    public List<Dicom> get(String name) {
        return this.stream()
                .filter(dicom -> name.equalsIgnoreCase(dicom.name()))
                .toList();
    }

    // todo optional
    public Optional<Dicom> getFirst(String name) {
        try {
            return Optional.of(get(name).getFirst());
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String name() {
        return name;
    }

    public String string() {
        return value;
    }

    public Optional<Integer> integer() {
        if (value == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public Optional<Double> floatingPoint() {
        if (value == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(Double.parseDouble(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public Optional<LocalDate> localDate() {
        if (value == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyyMMdd")));
        } catch (DateTimeParseException dateTimeParseException) {
            return Optional.empty();
        }
    }

    public Optional<LocalTime> localTime() {
        if (value == null) {
            return Optional.empty();
        }

        try {
            DateTimeFormatter timeFormatter;
            if (value.contains(".")) {
                timeFormatter = DateTimeFormatter.ofPattern("HHmmss.SSSSSS");
            } else {
                timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
            }

            return Optional.of(LocalTime.parse(value, timeFormatter));
        } catch (DateTimeParseException dateTimeParseException) {
            return Optional.empty();
        }
    }

    public Optional<LocalDateTime> localDateTime() {
        if (value == null) {
            return Optional.empty();
        }

        try {
            DateTimeFormatter timeFormatter;
            if (value.contains(".")) {
                timeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss.SSSSSS");
            } else {
                timeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            }

            return Optional.of(LocalDateTime.parse(value, timeFormatter));
        } catch (DateTimeParseException dateTimeParseException) {
            return Optional.empty();
        }
    }

    public Optional<Integer> height() {
        return floatingPoint().map(height -> (int) Math.round(height + 100));
    }

    public Optional<Integer> age() {
        if (value == null || !value.endsWith("Y")) {
            return Optional.empty();
        }

        try {
            return Optional.of(Integer.parseInt(value.substring(0, 3)));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public Optional<String> pregnancy() {
        if (value == null) {
            return Optional.empty();
        }

        return switch (value) {
            case "1" -> Optional.of("Not pregnant");
            case "2" -> Optional.of("Possibly pregnant");
            case "3" -> Optional.of("Pregnant");
            case "4" -> Optional.of("Unknown");
            case "5" -> Optional.of("Not applicable");
            default -> Optional.empty();
        };
    }

    @Override
    public String toString() {
        return name + " [" + size() + "]: " + value;
    }
}
