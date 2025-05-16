# NeoREM
Radiation Exposure Monitoring inspired by OpenREM

## Technology
- Java
  - Spring Boot
- React
- Sqlite
  - Flyway
- Docker

## Deployment

## Development

### Commands
```
mvn clean -Dmaven.wagon.http.ssl.insecure=true
mvn spring-boot:run -Dmaven.wagon.http.ssl.insecure=true
```

### Features
- [ ] Dicom import
- [ ] HL7 import
- [ ] user interface
- [ ] csv export
- [ ] xlsx export

## OpenREN Import

         *         all_mods["CT"] = {"inc": False, "mods": ["CT"]}
         *         all_mods["MG"] = {"inc": False, "mods": ["MG"]}
         *         all_mods["FL"] = {"inc": False, "mods": ["RF", "XA"]}
         *         all_mods["DX"] = {"inc": False, "mods": ["DX", "CR", "PX"]}
         *         all_mods["NM"] = {"inc": False, "mods": ["NM", "PT"]}
         *         all_mods["SR"] = {"inc": False, "mods": ["SR"]}

### CT
modalities:
- CT

fields:
-

### Fluro
modalities
- MG

fields:
-

### Mammo
modalities
- RF
- XA

fields:
- FilterMaterial
- KVP
- ExposureInuAs
- OrganDose
- XRayTubeCurrent
- ExposureTime
- FocalSpots
- AnodeTargetMaterial
- FieldOfViewDimensions
- ExposureControlMode
- Grid
- DistanceSourceToDetector
- DistanceSourceToEntrance
- BodyPartThickness
- CompressionForce
- EstimatedRadiographicMagnificationFactor
- PositionerPrimaryAngle
- SOPInstanceUID
- AcquisitionTime
- AcquisitionDate
- ProtocolName
- ImageLaterality
- Laterality
- EntranceDoseInmGy
- CommentsOnRadiationDose
- ExposureControlModeDescription
- Manufacturer
- InstitutionName
- InstitutionAddress
- StationName
- InstitutionalDepartmentName
- ManufacturerModelName
- DeviceSerialNumber
- SoftwareVersions
- GantryID
- SpatialResolution
- DateOfLastCalibration
- TimeOfLastCalibration
- PatientAge
- StudyInstanceUID
- ReferringPhysicianName
- StudyID
- AccessionNumber
- StudyDescription
- Modality
- PhysiciansOfRecord
- NameOfPhysiciansReadingStudy
- PerformingPhysicianName
- OperatorsName
- ProtocolName
- RequestedProcedureCodeSequence
- SOPInstanceUID
- AnatomicRegionSequence
- ViewCodeSequence
- StudyDate
- StudyTime

### radio
modalities
- DX
- CR
- PX

fields:
-

### NM/PET
modalities
- NM
- PT

fields:
-

### RDSR
modalities
- SR

fields:
-
