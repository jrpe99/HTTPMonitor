package dk.jrpe.monitor.source.httpaccess.simulate;

import java.net.MalformedURLException;
import java.util.Random;

import dk.jrpe.monitor.source.httpaccess.client.CXFWebServiceClient;
import dk.jrpe.monitor.source.httpaccess.to.HTTPAccessTOFactory;
import dk.jrpe.monitor.webservice.cxf.CXFWebServiceMode;

public class SimulatorCXFAsyncWebServiceClient {
    public static void main(String[] args) {
    	try {
			new SimulatorCXFAsyncWebServiceClient().simulate();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    private final CXFWebServiceClient client;
    
    public SimulatorCXFAsyncWebServiceClient() throws MalformedURLException {
        this.client = new CXFWebServiceClient(CXFWebServiceMode.ASYNC);
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