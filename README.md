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
- [ ] csv export
- [ ] xlsx export
- [ ] study querying
- [ ] task management
- [ ] indicators
- [ ] mappings
- [ ] messaging
- [ ] user management
- [ ] configuration

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
- ScanLength
- KVP
- XRayTubeCurrentInuA
- SeriesDescription
- AcquisitionType
- ExposureTime
- SingleCollimationWidth
- TotalCollimationWidth
- SpiralPitchFactor
- CTDIvol
- AcquisitionDateTime
- TotalNumberOfExposures
- CommentsOnRadiationDose
- CommentsOnRadiationDose
- StudyDescription
- StationName
- Manufacturer
- ManufacturerModelName
- InstitutionName
- InstitutionAddress
- StationName
- InstitutionalDepartmentName
- DeviceSerialNumber
- SoftwareVersions
- GantryID
- SpatialResolution
- DateOfLastCalibration
- TimeOfLastCalibration
- PatientAge
- PatientWeight
- StudyInstanceUID
- StudyDate
- StudyTime
- RequestingPhysician
- StudyID
- AccessionNumber
- ProtocolName
- OperatorsName
- RequestedProcedureDescription
- ScheduledProtocolCodeSequence
- ManufacturerModelName
- ManufacturerModelName

### Fluro
modalities
- MG

fields:
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
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
- FilterType
- FilterMaterial
- FilterThicknessMinimum
- FilterThicknessMaximum
- KVP
- ExposureInuAs
- Exposure
- ExposureIndex
- RelativeXRayExposure
- Sensitivity
- TargetExposureIndex
- DeviationIndex
- XRayTubeCurrent
- AverageXRayTubeCurrent
- ExposureTime
- FocalSpots
- FieldOfViewDimensions
- ExposureControlMode
- Grid
- GridAbsorbingMaterial
- GridSpacingMaterial
- GridThickness
- GridPitch
- GridAspectRatio
- GridPeriod
- GridFocalDistance
- DistanceSourceToDetector
- DistanceSourceToPatient
- DistanceSourceToIsocenter
- TableHeight
- EstimatedRadiographicMagnificationFactor
- PositionerPrimaryAngle
- PositionerSecondaryAngle
- ColumnAngulation
- TableHeadTiltAngle
- TableHorizontalRotationAngle
- TableCradleTiltAngle
- SOPInstanceUID
- AcquisitionTime
- ContentTime
- StudyTime
- AcquisitionDate
- ContentDate
- StudyDate
- ProtocolName
- Manufacturer
- SoftwareVersions
- ImageComments
- SeriesDescription
- PerformedProtocolCodeSequence
- SeriesDescription
- ImageLaterality
- ViewCodeSequence
- ViewPosition
- EntranceDoseInmGy
- CommentsOnRadiationDose
- ExposureControlModeDescription
- ImageAndFluoroscopyAreaDoseProduct
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
- PatientWeight
- PatientSize
- ReferringPhysicianName
- StudyID
- AccessionNumber
- StudyDescription
- SeriesDescription
- ProcedureCodeSequence
- Modality
- PhysiciansOfRecord
- NameOfPhysiciansReadingStudy
- PerformingPhysicianName
- OperatorsName
- ProcedureCodeSequence
- ProtocolName
- StudyDescription
- SeriesDescription
- RequestedProcedureCodeSequence
- RequestedProcedureCodeSequence
- RequestAttributesSequence
- ProcedureCodeSequence
- PerformedProtocolCodeSequence
- RequestAttributesSequence
- ProcedureCodeSequence
- PerformedProtocolCodeSequence
- Manufacturer
- ManufacturerModelName
- StudyInstanceUID
- StudyDate
- StudyTime

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
