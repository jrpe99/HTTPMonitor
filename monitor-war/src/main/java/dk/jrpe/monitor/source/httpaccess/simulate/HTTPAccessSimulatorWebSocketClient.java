package dk.jrpe.monitor.source.httpaccess.simulate;

import java.net.URISyntaxException;
import java.util.Random;

import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import dk.jrpe.monitor.service.command.CommandHandler;
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
        	sendToServer(HTTPAccessTOFactory.createSimulated());
        	int sleepTime = random.nextInt(500);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
            }
        }
    }
    
    public void sendToServer(HTTPAccessTO to) throws Exception {
        if (to.getHttpStatus().equals("200")) {
        	this.client.sendToServer(to, CommandHandler.CommandEnum.SEND_HTTP_SUCCESS_DATA.toString());
        	client.sendToServer(to, CommandHandler.CommandEnum.SEND_HTTP_SUCCESS_PER_MINUTE_DATA.toString());
        } else {
        	client.sendToServer(to, CommandHandler.CommandEnum.SEND_HTTP_FAILED_DATA.toString());
        	client.sendToServer(to, CommandHandler.CommandEnum.SEND_HTTP_FAILED_PER_MINUTE_DATA.toString());
        }
    }
}