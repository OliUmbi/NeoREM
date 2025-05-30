package ch.oliumbi.neorem.ingest;

import ch.oliumbi.neorem.entities.Device;
import ch.oliumbi.neorem.entities.Patient;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {

    public Device map(Device device, Dicom dicom) {
        device.setManufacturer(dicom
                .getFirst("Manufacturer")
                .map(Dicom::string)
                .orElse(null));

        device.setModel(dicom
                .getFirst("ManufacturerModelName")
                .map(Dicom::string)
                .orElse(null));

        device.setSerial(dicom
                .getFirst("DeviceSerialNumber")
                .map(Dicom::string)
                .orElse(null));

        device.setSoftware(dicom
                .getFirst("SoftwareVersions")
                .map(Dicom::string)
                .orElse(null));

        return device;
    }
}
