package ch.oliumbi.neorem.ingest;

import lombok.SneakyThrows;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.UID;
import org.dcm4che3.data.VR;
import org.dcm4che3.net.*;
import org.dcm4che3.net.pdu.AAssociateRQ;
import org.dcm4che3.net.pdu.PresentationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class DicomMove {

    @SneakyThrows
    public static void main(String[] args) throws Exception {

        String remoteAET = "MOCK_PACS";
        String remoteHost = "localhost";
        int remotePort = 105;

        Connection localConnection = new Connection();
        Connection remoteConnection = new Connection("MOCK_PACS", remoteHost, remotePort);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        ApplicationEntity applicationEntity = new ApplicationEntity("NEOREM_CLIENT");

        Device device = new Device("NEOREM_CLIENT");
        device.addApplicationEntity(applicationEntity);
        device.addConnection(localConnection);
        device.setExecutor(executorService);
        device.setScheduledExecutor(scheduledExecutorService);

        applicationEntity.addTransferCapability(new TransferCapability(
                "NEOREM_CLIENT",
                UID.StudyRootQueryRetrieveInformationModelMove,
                TransferCapability.Role.SCU,
                UID.ImplicitVRLittleEndian));
        applicationEntity.addConnection(localConnection);

        AAssociateRQ aAssociateRQ = new AAssociateRQ();
        aAssociateRQ.setCallingAET("NEOREM_CLIENT");
        aAssociateRQ.setCalledAET(remoteAET);
        aAssociateRQ.addPresentationContext(new PresentationContext(
                1,
                UID.Verification,
                UID.ImplicitVRLittleEndian));
        aAssociateRQ.addPresentationContext(new PresentationContext(
                3,
                UID.StudyRootQueryRetrieveInformationModelFind,
                UID.ImplicitVRLittleEndian));
        aAssociateRQ.addPresentationContext(new PresentationContext(
                5,
                UID.StudyRootQueryRetrieveInformationModelMove,
                UID.ImplicitVRLittleEndian));

        Association association = applicationEntity.connect(remoteConnection, aAssociateRQ);

        Attributes attributes = new Attributes();
        attributes.setString(Tag.QueryRetrieveLevel, VR.CS, "SERIES");
        attributes.setString(Tag.SeriesInstanceUID, VR.UI, "1.3.6.1.4.1.5962.99.1.3532166422.478333303.1485295916310.14.0");

        DimseRSP dimseRSP = association.cmove(UID.StudyRootQueryRetrieveInformationModelMove, Priority.NORMAL, attributes, UID.ImplicitVRLittleEndian, "NEOREM_PROVIDER");

        while (dimseRSP.next()) {
            Attributes dataset = dimseRSP.getDataset();

            System.out.println(dataset);
        }

        association.release();
    }
}
