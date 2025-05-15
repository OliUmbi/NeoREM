package ch.oliumbi.neorem.ingest;

import java.util.concurrent.Executors;

import org.dcm4che3.data.UID;
import org.dcm4che3.net.ApplicationEntity;
import org.dcm4che3.net.Association;
import org.dcm4che3.net.Connection;
import org.dcm4che3.net.Device;
import org.dcm4che3.net.pdu.AAssociateRQ;
import org.dcm4che3.net.pdu.PresentationContext;

public class DicomEcho {

    public static void main(String[] args) throws Exception {
        String remoteAET = "MOCK_PACS";
        String remoteHost = "localhost";
        int remotePort = 105;

        String localAET = "NEOREM";
        String localHost = "localhost";
        int localPort = 104;

        Connection localConnection = new Connection("NeoREM", localHost, localPort);
        Connection remoteConnection = new Connection("Mock Pacs", remoteHost, remotePort);

        ApplicationEntity applicationEntity = new ApplicationEntity(localAET);

        Device device = new Device("NeoREM");
        device.addApplicationEntity(applicationEntity);
        device.addConnection(localConnection);
        device.setExecutor(Executors.newSingleThreadExecutor());
        device.setScheduledExecutor(Executors.newSingleThreadScheduledExecutor());

        applicationEntity.addConnection(localConnection);

        AAssociateRQ aAssociateRQ = new AAssociateRQ();
        aAssociateRQ.setCallingAET(localAET);
        aAssociateRQ.setCalledAET(remoteAET);
        aAssociateRQ.addPresentationContext(new PresentationContext(1, UID.Verification, UID.ImplicitVRLittleEndian));

        Association association = applicationEntity.connect(remoteConnection, aAssociateRQ);

        association.cecho();

        association.release();
    }
}
