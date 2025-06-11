package ch.oliumbi.neorem.mappers;

import ch.oliumbi.neorem.entities.Device;
import ch.oliumbi.neorem.ingest.Dicom;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {

    // todo look at spring mappers and NullValuePropertyMappingStrategy

    public Device map(Dicom dicom) {

        Device device = new Device();

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
