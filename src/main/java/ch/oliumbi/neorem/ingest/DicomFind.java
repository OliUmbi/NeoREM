package ch.oliumbi.neorem.ingest;


import org.dcm4che3.data.*;
import org.dcm4che3.net.*;
import org.dcm4che3.net.pdu.AAssociateRQ;
import org.dcm4che3.net.pdu.PresentationContext;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

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
        calStart.set(1990, Calendar.JANUARY, 1, 0, 0, 0);
        calStart.set(Calendar.MILLISECOND, 0);
        Date startDate = calStart.getTime();
        Date endDate = new Date();

        Attributes attributes = new Attributes();
        attributes.setString(Tag.QueryRetrieveLevel, VR.CS, "STUDY");
        attributes.setDateRange(Tag.StudyDate, VR.DA, new DateRange(startDate, endDate));
        attributes.setNull(Tag.StudyInstanceUID, VR.UI);
        attributes.setNull(Tag.SeriesInstanceUID, VR.UI);
        attributes.setNull(Tag.Modality, VR.CS);

        DimseRSP dimseRSP = association.cfind(UID.StudyRootQueryRetrieveInformationModelFind, Priority.NORMAL, attributes, UID.ImplicitVRLittleEndian, 1000);

        while (dimseRSP.next()) {
            Attributes dataset = dimseRSP.getDataset();

            if (dataset == null) {
                continue;
            }

            System.out.println("StudyInstanceUID: " + dataset.getString(Tag.StudyInstanceUID));
            System.out.println("SeriesInstanceUID: " + dataset.getString(Tag.SeriesInstanceUID));
            System.out.println("Modality: " + dataset.getString(Tag.Modality));
        }

        association.release();
    }
}
