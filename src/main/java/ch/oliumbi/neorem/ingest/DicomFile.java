package ch.oliumbi.neorem.ingest;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.ElementDictionary;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.util.TagUtils;

import java.io.InputStream;

public class DicomFile {

    public static void main(String[] args) throws Exception {

        ElementDictionary elementDictionary = ElementDictionary.getStandardElementDictionary();

        for (int i = 1; i <= 8; i++) {
            try (InputStream is = DicomFile.class.getResourceAsStream("/test-" + i + ".dcm");
                 DicomInputStream dicomInputStream = new DicomInputStream(is)) {

                Attributes attributes = dicomInputStream.readDataset(-1);

                attributes.accept((attr, tag, vr, value) -> {
                    System.out.println(TagUtils.toString(tag) + " | " + elementDictionary.keywordOf(tag) + " = " + attr.getString(tag));
                    return true;
                }, true);
            }
        }
    }
}
