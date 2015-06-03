package dk.jrpe.monitor.source.httpaccess.simulate;

import java.util.Random;

import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import dk.jrpe.monitor.service.command.CommandHandler;
import dk.jrpe.monitor.source.httpaccess.client.HTTPAccessCXFWebServiceClient;

public class HTTPAccessSimulatorCXFWebServiceClient {
    public static void main(String[] args) {
        try {
            Random random = new Random();
            HTTPAccessCXFWebServiceClient client = new HTTPAccessCXFWebServiceClient();
            while (true) {
            	HTTPAccessTO data = HTTPAccessTOFactory.createSimulated();
            	
                if (data.getHttpStatus().equals("200")) {
                	client.sendToServer(data, CommandHandler.CommandEnum.SEND_HTTP_SUCCESS_DATA.toString());
                	client.sendToServer(data, CommandHandler.CommandEnum.SEND_HTTP_SUCCESS_PER_MINUTE_DATA.toString());
                } else {
                	client.sendToServer(data, CommandHandler.CommandEnum.SEND_HTTP_FAILED_DATA.toString());
                	client.sendToServer(data, CommandHandler.CommandEnum.SEND_HTTP_FAILED_PER_MINUTE_DATA.toString());
                }
                int sleepTime = random.nextInt(500);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}