package dk.jrpe.monitor.source.httpaccess.simulate;

import java.net.URL;
import java.util.Random;

import dk.jrpe.monitor.service.command.CommandHandler;
import dk.jrpe.monitor.source.httpaccess.to.HTTPAccessTOFactory;
import dk.jrpe.monitor.webservice.endpoint.generated.HTTPAccessData;
import dk.jrpe.monitor.webservice.endpoint.generated.HTTPAccessDataPort;
import dk.jrpe.monitor.webservice.endpoint.generated.HTTPAccessDataService;

public class HTTPAccessWebServiceClient {
    public static void main(String[] args) {
        new HTTPAccessWebServiceClient().simulateWithCXFWebService();
    }

    public void simulateWithCXFWebService() {
        try {
            URL wsdlURL = new URL("http://localhost:8080/monitor/services/HTTPAccessServicePort?WSDL");

            HTTPAccessDataPort httpAccessDataPort = new HTTPAccessDataService(wsdlURL).getHTTPAccessDataPort();
            Random random = new Random();
            while (true) {
            	HTTPAccessData data = HTTPAccessTOFactory.createSimulated(HTTPAccessTOFactory.createSimulated());
            	
                if (data.getHttpStatus().equals("200")) {
                	data.setCommand(CommandHandler.CommandEnum.SEND_HTTP_SUCCESS_DATA.toString());
                	httpAccessDataPort.sendHTTPAccessData(data);
                    data.setCommand(CommandHandler.CommandEnum.SEND_HTTP_SUCCESS_PER_MINUTE_DATA.toString());
                	httpAccessDataPort.sendHTTPAccessData(data);
                } else {
                    data.setCommand(CommandHandler.CommandEnum.SEND_HTTP_FAILED_DATA.toString());
                	httpAccessDataPort.sendHTTPAccessData(data);
                    data.setCommand(CommandHandler.CommandEnum.SEND_HTTP_FAILED_PER_MINUTE_DATA.toString());
                	httpAccessDataPort.sendHTTPAccessData(data);
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