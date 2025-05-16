package ch.oliumbi.neorem.ingest;

import org.dcm4che3.data.*;
import org.dcm4che3.net.*;
import org.dcm4che3.net.pdu.AAssociateRQ;
import org.dcm4che3.net.pdu.PresentationContext;

import java.util.*;
import java.util.concurrent.*;

public class DicomFind {


    public static void main(String[] args) throws Exception {
        String remoteAET = "MOCK_PACS";
        String remoteHost = "localhost";
        int remotePort = 105;

        String localAET = "NEOREM_CLIENT";

        Connection localConnection = new Connection();
        Connection remoteConnection = new Connection("Mock Pacs", remoteHost, remotePort);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        TransferCapability transferCapability = new TransferCapability(
                "NEOREM_CLIENT",
                UID.StudyRootQueryRetrieveInformationModelFind,
                TransferCapability.Role.SCU,
                UID.ImplicitVRLittleEndian);

        ApplicationEntity applicationEntity = new ApplicationEntity(localAET);

        Device device = new Device("NEOREM_CLIENT");
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

        // cfind studies
        // cfind series in study


        /**
         *
         *         all_mods["CT"] = {"inc": False, "mods": ["CT"]}
         *         all_mods["MG"] = {"inc": False, "mods": ["MG"]}
         *         all_mods["FL"] = {"inc": False, "mods": ["RF", "XA"]}
         *         all_mods["DX"] = {"inc": False, "mods": ["DX", "CR", "PX"]}
         *         all_mods["NM"] = {"inc": False, "mods": ["NM", "PT"]}
         *         all_mods["SR"] = {"inc": False, "mods": ["SR"]}
         *
         */

        Attributes attributes = new Attributes();
        attributes.setString(Tag.QueryRetrieveLevel, VR.CS, "SERIES");
        attributes.setString(Tag.StudyInstanceUID, VR.UI, "1.2.840.113619.6.95.31.0.3.4.1.4400.13.9022703");
        attributes.setNull(Tag.SeriesInstanceUID, VR.UI);
        attributes.setNull(Tag.SOPInstanceUID, VR.UI);
        attributes.setNull(Tag.AccessionNumber, VR.SH);
        attributes.setDateRange(Tag.StudyDate, VR.DA, new DateRange(startDate, endDate));
        attributes.setNull(Tag.ContentDate, VR.DA);
        attributes.setNull(Tag.StudyTime, VR.TM);
        attributes.setNull(Tag.ContentTime, VR.TM);
        attributes.setNull(Tag.Modality, VR.CS);
        attributes.setNull(Tag.ProcedureCodeSequence, VR.SQ);
        attributes.setNull(Tag.RequestedProcedureCodeSequence, VR.SQ);
        attributes.setNull(Tag.PerformedProcedureCodeSequence, VR.SQ);
        attributes.setNull(Tag.RequestedProcedureDescription, VR.LO);
        attributes.setNull(Tag.ScheduledProcedureStepDescription, VR.LO);
        attributes.setNull(Tag.PatientName, VR.PN);
        attributes.setNull(Tag.PatientID, VR.LO);
        attributes.setNull(Tag.PatientSex, VR.CS);
        attributes.setNull(Tag.PatientAge, VR.AS);
        attributes.setNull(Tag.PatientSize, VR.DS);
        attributes.setNull(Tag.PatientWeight, VR.DS);
        attributes.setNull(Tag.StudyDescription, VR.LO);
        attributes.setNull(Tag.ManufacturerModelName, VR.LO);
        attributes.setNull(Tag.ReferringPhysicianName, VR.PN);
        attributes.setNull(Tag.PhysiciansOfRecord, VR.PN);
        attributes.setNull(Tag.NameOfPhysiciansReadingStudy, VR.PN);
        attributes.setNull(Tag.PerformingPhysicianName, VR.PN);
        attributes.setNull(Tag.ModalitiesInStudy, VR.CS);
        attributes.setNull(Tag.NumberOfStudyRelatedSeries, VR.CS);
        attributes.setNull(Tag.NumberOfStudyRelatedInstances, VR.CS);

        DimseRSP dimseRSP = association.cfind(UID.StudyRootQueryRetrieveInformationModelFind, Priority.NORMAL, attributes, UID.ImplicitVRLittleEndian, 1000);

        while (dimseRSP.next()) {
            Attributes command = dimseRSP.getCommand();
            System.out.println("!!!!!!!!!!!!!:" + command);
            Attributes dataset = dimseRSP.getDataset();
            System.out.println("QueryRetrieveLevel: " + dataset.getString(Tag.QueryRetrieveLevel));
            System.out.println("StudyInstanceUID: " + dataset.getString(Tag.StudyInstanceUID));
            System.out.println("SeriesInstanceUID: " + dataset.getString(Tag.SeriesInstanceUID));
            System.out.println("SOPInstanceUID: " + dataset.getString(Tag.SOPInstanceUID));
            System.out.println("AccessionNumber: " + dataset.getString(Tag.AccessionNumber));
            System.out.println("StudyDate: " + dataset.getString(Tag.StudyDate));
            System.out.println("ContentDate: " + dataset.getString(Tag.ContentDate));
            System.out.println("StudyTime: " + dataset.getString(Tag.StudyTime));
            System.out.println("ContentTime: " + dataset.getString(Tag.ContentTime));
            System.out.println("Modality: " + dataset.getString(Tag.Modality));
            System.out.println("ProcedureCodeSequence: " + dataset.getString(Tag.ProcedureCodeSequence));
            System.out.println("RequestedProcedureCodeSequence: " + dataset.getString(Tag.RequestedProcedureCodeSequence));
            System.out.println("PerformedProcedureCodeSequence: " + dataset.getString(Tag.PerformedProcedureCodeSequence));
            System.out.println("RequestedProcedureDescription: " + dataset.getString(Tag.RequestedProcedureDescription));
            System.out.println("ScheduledProcedureStepDescription: " + dataset.getString(Tag.ScheduledProcedureStepDescription));
            System.out.println("PatientName: " + dataset.getString(Tag.PatientName));
            System.out.println("PatientID: " + dataset.getString(Tag.PatientID));
            System.out.println("PatientSex: " + dataset.getString(Tag.PatientSex));
            System.out.println("PatientAge: " + dataset.getString(Tag.PatientAge));
            System.out.println("PatientSize: " + dataset.getString(Tag.PatientSize));
            System.out.println("PatientWeight: " + dataset.getString(Tag.PatientWeight));
            System.out.println("StudyDescription: " + dataset.getString(Tag.StudyDescription));
            System.out.println("ManufacturerModelName: " + dataset.getString(Tag.ManufacturerModelName));
            System.out.println("ReferringPhysicianName: " + dataset.getString(Tag.ReferringPhysicianName));
            System.out.println("PhysiciansOfRecord: " + dataset.getString(Tag.PhysiciansOfRecord));
            System.out.println("NameOfPhysiciansReadingStudy: " + dataset.getString(Tag.NameOfPhysiciansReadingStudy));
            System.out.println("PerformingPhysicianName: " + dataset.getString(Tag.PerformingPhysicianName));
            System.out.println("ModalitiesInStudy: " + dataset.getString(Tag.ModalitiesInStudy));
            System.out.println("NumberOfStudyRelatedSeries: " + dataset.getString(Tag.NumberOfStudyRelatedSeries));
            System.out.println("NumberOfStudyRelatedInstances: " + dataset.getString(Tag.NumberOfStudyRelatedInstances));
        }

        association.release();
    }
}
