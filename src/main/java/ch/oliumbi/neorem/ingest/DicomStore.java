package ch.oliumbi.neorem.ingest;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.net.*;
import org.dcm4che3.net.pdu.PresentationContext;
import org.dcm4che3.net.service.BasicCEchoSCP;
import org.dcm4che3.net.service.BasicCStoreSCP;
import org.dcm4che3.net.service.DicomServiceRegistry;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class DicomStore {

    public static void main(String[] args) throws Exception {
        String localAET = "NEOREM_PROVIDER";
        String localHost = "0.0.0.0";
        int localPort = 8004;

        Connection localConnection = new Connection("NEOREM_PROVIDER", localHost, localPort);
        localConnection.setBindAddress("localhost");
        localConnection.setMaxOpsInvoked(0);
        localConnection.setMaxOpsPerformed(0);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        DicomServiceRegistry serviceRegistry = new DicomServiceRegistry();
        serviceRegistry.addDicomService(new BasicCEchoSCP());
        serviceRegistry.addDicomService(new BasicCStoreSCP("*") {
            @Override
            protected void store(Association as, PresentationContext pc, Attributes rq,
                                 PDVInputStream data, Attributes rsp) throws IOException {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!" + data);
            }
        });

        ApplicationEntity applicationEntity = new ApplicationEntity(localAET);

        Device device = new Device("NEOREM_PROVIDER");
        device.addApplicationEntity(applicationEntity);
        device.addConnection(localConnection);
        device.setExecutor(executorService);
        device.setScheduledExecutor(scheduledExecutorService);

        applicationEntity.setAssociationAcceptor(true);
        applicationEntity.addConnection(localConnection);
        applicationEntity.addTransferCapability(new TransferCapability(
                "NEOREM_PROVIDER",
                "*",
                TransferCapability.Role.SCP,
                "*"));

        device.setDimseRQHandler(serviceRegistry);

        device.bindConnections();
    }
}
