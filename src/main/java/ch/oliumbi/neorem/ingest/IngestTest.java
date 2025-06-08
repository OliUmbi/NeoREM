package ch.oliumbi.neorem.ingest;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

@Component
public class IngestTest {

    private final Ingest ingest;

    public IngestTest(Ingest ingest) {
        this.ingest = ingest;
    }

    // @PostConstruct
    public void test() throws Exception {
        List<String> folders = List.of("/ct", "/fl", "/mg", "/nm", "/rg");

        for (String folder : folders) {

            URL resource = DicomFile.class.getResource(folder);

            for (File file : Paths.get(resource.toURI()).toFile().listFiles()) {
                ingest.ingest(file);
            }
        }
    }
}
