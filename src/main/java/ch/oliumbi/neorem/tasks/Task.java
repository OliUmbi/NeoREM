package ch.oliumbi.neorem.tasks;

public interface Task extends Runnable {

    // todo needed jobs housekeeping (remove execution logs that are old, delete stray dicom files, delete old exports + files, delete old imports)

    String name();
}
