package dk.jrpe.monitor.source.httpaccess.simulate;

import java.net.URISyntaxException;
import java.util.Random;

import dk.jrpe.monitor.source.httpaccess.client.WebSocketClient;
import dk.jrpe.monitor.source.httpaccess.to.HTTPAccessTOFactory;

public class SimulatorWebSocketClient {
    public static void main(String[] args) {
    	try {
			new SimulatorWebSocketClient().simulate();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    private final WebSocketClient client;
    
    public SimulatorWebSocketClient() throws URISyntaxException {
        this.client = new WebSocketClient();
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