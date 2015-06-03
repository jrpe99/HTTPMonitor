package dk.jrpe.monitor.source.httpaccess.client;

import java.net.MalformedURLException;
import java.net.URL;

import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import dk.jrpe.monitor.source.httpaccess.simulate.HTTPAccessTOFactory;
import dk.jrpe.monitor.webservice.endpoint.generated.HTTPAccessData;
import dk.jrpe.monitor.webservice.endpoint.generated.HTTPAccessDataPort;
import dk.jrpe.monitor.webservice.endpoint.generated.HTTPAccessDataService;

public class HTTPAccessCXFWebServiceClient {

    private final HTTPAccessDataPort httpAccessDataPort;

	public HTTPAccessCXFWebServiceClient() throws MalformedURLException {
        URL wsdlURL = new URL("http://localhost:8080/monitor/services/HTTPAccessServicePort?WSDL");
		httpAccessDataPort = new HTTPAccessDataService(wsdlURL).getHTTPAccessDataPort();
	}

	public void sendToServer(HTTPAccessTO to, String command) throws Exception {
    	HTTPAccessData data = HTTPAccessTOFactory.convertToCXFObject(to);
    	data.setCommand(command);
    	httpAccessDataPort.sendHTTPAccessData(data);
	}
}