package dk.jrpe.monitor.source.httpaccess.simulate;

import java.net.URISyntaxException;
import java.util.Random;

import dk.jrpe.monitor.source.httpaccess.client.HTTPAccessWebSocketClient;

public class HTTPAccessSimulatorWebSocketClient {
    public static void main(String[] args) {
    	try {
			new HTTPAccessSimulatorWebSocketClient().simulate();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    private final HTTPAccessWebSocketClient client;
    
    public HTTPAccessSimulatorWebSocketClient() throws URISyntaxException {
        this.client = new HTTPAccessWebSocketClient();
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