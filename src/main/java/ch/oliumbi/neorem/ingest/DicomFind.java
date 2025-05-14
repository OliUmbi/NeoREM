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

        Attributes attributes = new Attributes();
        attributes.setString(Tag.QueryRetrieveLevel, VR.CS, "STUDY");

        Calendar calStart = Calendar.getInstance();
        calStart.set(2023, Calendar.JANUARY, 1, 0, 0, 0);
        calStart.set(Calendar.MILLISECOND, 0);
        Date startDate = calStart.getTime();
        Date endDate = new Date();
        attributes.setDateRange(Tag.StudyDate, VR.DA, new DateRange(startDate, endDate));

        attributes.setNull(Tag.StudyInstanceUID, VR.UI);
        attributes.setNull(Tag.PatientName, VR.PN);
        attributes.setNull(Tag.PatientID, VR.LO);
        attributes.setNull(Tag.StudyDescription, VR.LO);
        attributes.setNull(Tag.ModalitiesInStudy, VR.CS);

        DimseRSP dimseRSP = association.cfind(UID.StudyRootQueryRetrieveInformationModelFind, Priority.NORMAL, attributes, UID.ImplicitVRLittleEndian, 1000);

        while (dimseRSP.next()) {
            Attributes command = dimseRSP.getCommand();
            System.out.println("Command: " + command);
            System.out.println("StudyInstanceUID: " + command.getString(Tag.StudyInstanceUID)); // Should return StudyInstanceUID if available
            Attributes dataset = dimseRSP.getDataset();
            System.out.println("Dataset: " + dataset);
        }

        association.release();
    }
}
