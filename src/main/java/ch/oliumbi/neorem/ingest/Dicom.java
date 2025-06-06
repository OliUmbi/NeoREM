package ch.oliumbi.neorem.ingest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Dicom extends ArrayList<Dicom> {

    private String name;
    private String value;

    public List<Dicom> all(String name) {
        return this.stream()
                .filter(dicom -> name.equalsIgnoreCase(dicom.name()))
                .toList();
    }

    public Optional<Dicom> first() {
        try {
            return Optional.of(this.getFirst());
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public Optional<Dicom> first(String name) {
        try {
            return Optional.of(all(name).getFirst());
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

    public Optional<String> string() {
        return Optional.ofNullable(value);
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

    public Optional<Double> medianFloatingPoint(String name) {
        if (value == null) {
            return Optional.empty();
        }

        List<Double> doubles = new ArrayList<>();

        for (Dicom dicom : all(name)) {
            dicom.floatingPoint().ifPresent(doubles::add);
        }

        if (doubles.isEmpty()) {
            return Optional.empty();
        }

        Collections.sort(doubles);

        if (doubles.size() % 2 == 0) {
            Double lower = doubles.get(doubles.size() / 2 - 1);
            Double upper = doubles.get(doubles.size() / 2);

            return Optional.of((lower + upper) / 2);
        }

        return Optional.of(doubles.get(doubles.size() / 2));
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
            return Optional.of(LocalTime.parse(value.split("\\.")[0], DateTimeFormatter.ofPattern("HHmmss")));
        } catch (DateTimeParseException dateTimeParseException) {
            return Optional.empty();
        }
    }

    public Optional<LocalDateTime> localDateTime() {
        if (value == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(LocalDateTime.parse(value.split("\\.")[0], DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        } catch (DateTimeParseException dateTimeParseException) {
            return Optional.empty();
        }
    }

    public Optional<String> modality() {
        return switch (value) {
            case "CT" -> Optional.of("Computer Tomography");
            case "MG" -> Optional.of("Mammography");
            case "RF", "XA", "Fluoroscopy" -> Optional.of("Fluoroscopy");
            case "DX", "CR", "PX" -> Optional.of("Radiography");
            case "NM", "PT" -> Optional.of("Nuclear Medicine");
            default -> Optional.empty();
        };
    }

    public Optional<String> laterality() {
        return switch (value) {
            case "L" -> Optional.of("Left");
            case "R" -> Optional.of("Right");
            default -> Optional.empty();
        };
    }


    public Optional<String> material() {
        return switch (value) {
            case "Rhodium or Rhodium compound" -> Optional.of("Rhodium");
            case "Silver or Silver compound" -> Optional.of("Silver");
            case "Molybdenum or Molybdenum compound" -> Optional.of("Molybdenum");
            case "Aluminum or Aluminum compound", "Aluminium or Aluminium Compound" -> Optional.of("Aluminum");
            case "Tungsten or Tungsten compound" -> Optional.of("Tungsten");
            case null -> Optional.empty();
            default -> Optional.of(value);
        };
    }

    public Optional<Integer> height() {
        return floatingPoint().map(height -> (int) Math.round(height + 100));
    }

    public Optional<Integer> age() {
        if (value == null || !value.endsWith("Y")) {
            return Optional.empty();
        }

        try {
            return Optional.of(Integer.parseInt(value.replace("Y", "")));
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
        StringBuilder result = new StringBuilder();
        result.append(name).append(" : ").append(value);

        if (size() > 0) {
            result.append(" [").append(size()).append("]");
        }

        result.append("\n");

        for (Dicom child : this) {
            for (String line : child.toString().split("\n")) {
                result.append("│  ").append(line);
                result.append("\n");
            }
        }

        return result.toString();
    }
}
