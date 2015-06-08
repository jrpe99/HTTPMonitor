package dk.jrpe.monitor.source.httpaccess.simulate;

import java.net.MalformedURLException;
import java.util.Random;

import dk.jrpe.monitor.source.httpaccess.client.CXFSyncWebServiceClient;
import dk.jrpe.monitor.source.httpaccess.to.HTTPAccessTOFactory;

public class SimulatorCXFSyncWebServiceClient {
    public static void main(String[] args) {
    	try {
			new SimulatorCXFSyncWebServiceClient().simulate();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    private final CXFSyncWebServiceClient client;
    
    public SimulatorCXFSyncWebServiceClient() throws MalformedURLException {
        this.client = new CXFSyncWebServiceClient();
	}

	public void simulate() throws Exception {
        Random random = new Random();
        while (true) {
        	this.client.sendToServer(HTTPAccessTOFactory.createSimulated());
        	int sleepTime = random.nextInt(500);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
            }
        }
    }
}