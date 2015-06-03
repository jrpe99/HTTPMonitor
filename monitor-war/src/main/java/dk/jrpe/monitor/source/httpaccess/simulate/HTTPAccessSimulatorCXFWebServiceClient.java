package dk.jrpe.monitor.source.httpaccess.simulate;

import java.net.MalformedURLException;
import java.util.Random;

import dk.jrpe.monitor.source.httpaccess.client.HTTPAccessCXFWebServiceClient;

public class HTTPAccessSimulatorCXFWebServiceClient {
    public static void main(String[] args) {
    	try {
			new HTTPAccessSimulatorCXFWebServiceClient().simulate();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    private final HTTPAccessCXFWebServiceClient client;
    
    public HTTPAccessSimulatorCXFWebServiceClient() throws MalformedURLException {
        this.client = new HTTPAccessCXFWebServiceClient();
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