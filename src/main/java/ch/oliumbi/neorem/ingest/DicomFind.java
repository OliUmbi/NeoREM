package ch.oliumbi.neorem.ingest;

import org.dcm4che3.data.*;
import org.dcm4che3.net.*;
import org.dcm4che3.net.pdu.AAssociateRQ;
import org.dcm4che3.net.pdu.PresentationContext;

import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DicomFind {


    public static void main(String[] args) throws Exception {
        String remoteAET = "MOCK_PACS";
        String remoteHost = "localhost";
        int remotePort = 105;

        String localAET = "NEOREM";
        String localHost = "localhost";
        int localPort = 104;

        Connection localConnection = new Connection("NeoREM", localHost, localPort);
        Connection remoteConnection = new Connection("Mock Pacs", remoteHost, remotePort);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        TransferCapability transferCapability = new TransferCapability(
                "NeoREM",
                UID.StudyRootQueryRetrieveInformationModelFind,
                TransferCapability.Role.SCU,
                UID.ImplicitVRLittleEndian);

        ApplicationEntity applicationEntity = new ApplicationEntity(localAET);

        Device device = new Device("NeoREM");
        device.addApplicationEntity(applicationEntity);
        device.addConnection(localConnection);
        device.setExecutor(executorService);
        device.setScheduledExecutor(scheduledExecutorService);

        applicationEntity.addTransferCapability(transferCapability);
        applicationEntity.addConnection(localConnection);

        AAssociateRQ aAssociateRQ = new AAssociateRQ();
        aAssociateRQ.setCallingAET(localAET);
        aAssociateRQ.setCalledAET(remoteAET);
        aAssociateRQ.addPresentationContext(new PresentationContext(
                1,
                UID.Verification,
                UID.ImplicitVRLittleEndian));
        aAssociateRQ.addPresentationContext(new PresentationContext(
                3,
                UID.StudyRootQueryRetrieveInformationModelFind,
                UID.ImplicitVRLittleEndian));

        Association association = applicationEntity.connect(remoteConnection, aAssociateRQ);


        Calendar calStart = Calendar.getInstance();
        calStart.set(2006, Calendar.JANUARY, 1, 0, 0, 0);
        calStart.set(Calendar.MILLISECOND, 0);
        Date startDate = calStart.getTime();
        Date endDate = new Date();

        /**
         * mock-pacs  | W0514 20:25:25.067394          DICOM-3 ResourceFinder.cpp:942] W001: Accessing DICOM tags from storage when accessing study fc06fc2d-aa48f087-c3dd986b-455a88aa-7f9b72ef: 0008,0018;0008,0021;0008,0023;0008,0031;0008,0033;0008,0060;0008,1010;0008,1032;0008,103e;0008,1048;0008,1050;0008,1060;0008,1070;0008,1090;0010,1010;0010,1020;0010,1030;0020,000e;0032,1064;0040,0007;0040,0254;0040,a372
         *
         * mock-pacs  | W0514 20:26:23.134576          DICOM-0 ResourceFinder.cpp:788] W005: Requested tag 0008,0018 should only be read at the instance level
         * mock-pacs  | W0514 20:26:23.134624          DICOM-0 ResourceFinder.cpp:762] W005: Requested tag 0008,0021 should only be read at the series or instance level
         * mock-pacs  | W0514 20:26:23.134633          DICOM-0 ResourceFinder.cpp:762] W005: Requested tag 0008,0031 should only be read at the series or instance level
         * mock-pacs  | W0514 20:26:23.134639          DICOM-0 ResourceFinder.cpp:762] W005: Requested tag 0008,0060 should only be read at the series or instance level
         * mock-pacs  | W0514 20:26:23.134645          DICOM-0 ResourceFinder.cpp:762] W005: Requested tag 0008,1010 should only be read at the series or instance level
         * mock-pacs  | W0514 20:26:23.134650          DICOM-0 ResourceFinder.cpp:762] W005: Requested tag 0008,103e should only be read at the series or instance level
         * mock-pacs  | W0514 20:26:23.134655          DICOM-0 ResourceFinder.cpp:762] W005: Requested tag 0008,1070 should only be read at the series or instance level
         * mock-pacs  | W0514 20:26:23.134664          DICOM-0 ResourceFinder.cpp:762] W005: Requested tag 0020,000e should only be read at the series or instance level
         * mock-pacs  | W0514 20:26:23.134672          DICOM-0 ResourceFinder.cpp:762] W005: Requested tag 0040,0254 should only be read at the series or instance level
         */

        Attributes attributes = new Attributes();
        attributes.setString(Tag.QueryRetrieveLevel, VR.CS, "STUDY");
        attributes.setNull(Tag.StudyInstanceUID, VR.UI);
        attributes.setNull(Tag.SeriesInstanceUID, VR.UI);
        attributes.setNull(Tag.SOPInstanceUID, VR.UI);
        attributes.setNull(Tag.AccessionNumber, VR.SH);
        attributes.setDateRange(Tag.StudyDate, VR.DA, new DateRange(startDate, endDate));
        attributes.setNull(Tag.ContentDate, VR.DA);
        attributes.setNull(Tag.SeriesDate, VR.DA);
        attributes.setNull(Tag.StudyTime, VR.TM);
        attributes.setNull(Tag.ContentTime, VR.TM);
        attributes.setNull(Tag.SeriesTime, VR.TM);
        attributes.setNull(Tag.Modality, VR.CS);
        attributes.setNull(Tag.ProcedureCodeSequence, VR.SQ);
        attributes.setNull(Tag.RequestedProcedureCodeSequence, VR.SQ);
        attributes.setNull(Tag.PerformedProcedureCodeSequence, VR.SQ);
        attributes.setNull(Tag.RequestedProcedureDescription, VR.LO);
        attributes.setNull(Tag.ScheduledProcedureStepDescription, VR.LO);
        attributes.setNull(Tag.PerformedProcedureStepDescription, VR.LO);
        attributes.setNull(Tag.PatientName, VR.PN);
        attributes.setNull(Tag.PatientID, VR.LO);
        attributes.setNull(Tag.PatientSex, VR.CS);
        attributes.setNull(Tag.PatientAge, VR.AS);
        attributes.setNull(Tag.PatientSize, VR.DS);
        attributes.setNull(Tag.PatientWeight, VR.DS);
        attributes.setNull(Tag.StudyDescription, VR.LO);
        attributes.setNull(Tag.SeriesDescription, VR.LO);
        attributes.setNull(Tag.StationName, VR.SH);
        attributes.setNull(Tag.ManufacturerModelName, VR.LO);
        attributes.setNull(Tag.ReferringPhysicianName, VR.PN);
        attributes.setNull(Tag.PhysiciansOfRecord, VR.PN);
        attributes.setNull(Tag.NameOfPhysiciansReadingStudy, VR.PN);
        attributes.setNull(Tag.PerformingPhysicianName, VR.PN);
        attributes.setNull(Tag.OperatorsName, VR.PN);
        attributes.setNull(Tag.ModalitiesInStudy, VR.CS);
        attributes.setNull(Tag.NumberOfStudyRelatedSeries, VR.CS);
        attributes.setNull(Tag.NumberOfStudyRelatedInstances, VR.CS);

        DimseRSP dimseRSP = association.cfind(UID.StudyRootQueryRetrieveInformationModelFind, Priority.NORMAL, attributes, UID.ImplicitVRLittleEndian, 1000);

        while (dimseRSP.next()) {
            Attributes command = dimseRSP.getCommand();
            Attributes dataset = dimseRSP.getDataset();
            System.out.println("QueryRetrieveLevel: " + dataset.getString(Tag.QueryRetrieveLevel));
            System.out.println("StudyInstanceUID: " + dataset.getString(Tag.StudyInstanceUID));
            System.out.println("SeriesInstanceUID: " + dataset.getString(Tag.SeriesInstanceUID));
            System.out.println("SOPInstanceUID: " + dataset.getString(Tag.SOPInstanceUID));
            System.out.println("AccessionNumber: " + dataset.getString(Tag.AccessionNumber));
            System.out.println("StudyDate: " + dataset.getString(Tag.StudyDate));
            System.out.println("ContentDate: " + dataset.getString(Tag.ContentDate));
            System.out.println("SeriesDate: " + dataset.getString(Tag.SeriesDate));
            System.out.println("StudyTime: " + dataset.getString(Tag.StudyTime));
            System.out.println("ContentTime: " + dataset.getString(Tag.ContentTime));
            System.out.println("SeriesTime: " + dataset.getString(Tag.SeriesTime));
            System.out.println("Modality: " + dataset.getString(Tag.Modality));
            System.out.println("ProcedureCodeSequence: " + dataset.getString(Tag.ProcedureCodeSequence));
            System.out.println("RequestedProcedureCodeSequence: " + dataset.getString(Tag.RequestedProcedureCodeSequence));
            System.out.println("PerformedProcedureCodeSequence: " + dataset.getString(Tag.PerformedProcedureCodeSequence));
            System.out.println("RequestedProcedureDescription: " + dataset.getString(Tag.RequestedProcedureDescription));
            System.out.println("ScheduledProcedureStepDescription: " + dataset.getString(Tag.ScheduledProcedureStepDescription));
            System.out.println("PerformedProcedureStepDescription: " + dataset.getString(Tag.PerformedProcedureStepDescription));
            System.out.println("PatientName: " + dataset.getString(Tag.PatientName));
            System.out.println("PatientID: " + dataset.getString(Tag.PatientID));
            System.out.println("PatientSex: " + dataset.getString(Tag.PatientSex));
            System.out.println("PatientAge: " + dataset.getString(Tag.PatientAge));
            System.out.println("PatientSize: " + dataset.getString(Tag.PatientSize));
            System.out.println("PatientWeight: " + dataset.getString(Tag.PatientWeight));
            System.out.println("StudyDescription: " + dataset.getString(Tag.StudyDescription));
            System.out.println("SeriesDescription: " + dataset.getString(Tag.SeriesDescription));
            System.out.println("StationName: " + dataset.getString(Tag.StationName));
            System.out.println("ManufacturerModelName: " + dataset.getString(Tag.ManufacturerModelName));
            System.out.println("ReferringPhysicianName: " + dataset.getString(Tag.ReferringPhysicianName));
            System.out.println("PhysiciansOfRecord: " + dataset.getString(Tag.PhysiciansOfRecord));
            System.out.println("NameOfPhysiciansReadingStudy: " + dataset.getString(Tag.NameOfPhysiciansReadingStudy));
            System.out.println("PerformingPhysicianName: " + dataset.getString(Tag.PerformingPhysicianName));
            System.out.println("OperatorsName: " + dataset.getString(Tag.OperatorsName));
            System.out.println("ModalitiesInStudy: " + dataset.getString(Tag.ModalitiesInStudy));
            System.out.println("NumberOfStudyRelatedSeries: " + dataset.getString(Tag.NumberOfStudyRelatedSeries));
            System.out.println("NumberOfStudyRelatedInstances: " + dataset.getString(Tag.NumberOfStudyRelatedInstances));
        }

        association.release();
    }
}
