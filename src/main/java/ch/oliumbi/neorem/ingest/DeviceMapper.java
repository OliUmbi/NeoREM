package ch.oliumbi.neorem.ingest;

import ch.oliumbi.neorem.entities.Device;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {

    public Device map(Device device, Dicom dicom) {
        device.setManufacturer(dicom
                .first("Manufacturer")
                .flatMap(Dicom::string)
                .orElse(null));

        device.setModel(dicom
                .first("ManufacturerModelName")
                .flatMap(Dicom::string)
                .orElse(null));

        device.setSerial(dicom
                .first("DeviceSerialNumber")
                .flatMap(Dicom::string)
                .orElse(null));

        device.setSoftware(dicom
                .first("SoftwareVersions")
                .flatMap(Dicom::string)
                .orElse(null));

        return device;
    }
}
