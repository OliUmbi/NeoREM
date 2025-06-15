package ch.oliumbi.neorem.tasks;

import java.util.Map;

public interface Task {

    // todo needed jobs housekeeping (remove execution logs that are old, delete stray dicom files, delete old exports + files, delete old imports)

    String name();

    void run(Map<String, String> parameters);
}
