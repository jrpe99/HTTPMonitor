package dk.jrpe.monitor.source.httpaccess.client;

import java.net.MalformedURLException;
import java.net.URL;

import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import dk.jrpe.monitor.source.httpaccess.simulate.HTTPAccessTOFactory;
import dk.jrpe.monitor.webservice.cxf.generated.endpoint.sync.HTTPAccessData;
import dk.jrpe.monitor.webservice.cxf.generated.endpoint.sync.HTTPAccessDataPort;
import dk.jrpe.monitor.webservice.cxf.generated.endpoint.sync.HTTPAccessDataService;

/**
 * Client for sending HTTP access data to the server using a CXF Web-service.
 * 
 * @author Jörgen Persson
 *
 */
public class HTTPAccessCXFWebServiceClient extends HTTPAccessWebClient {

    private final HTTPAccessDataPort httpAccessDataPort;

    /**
     * Initialize the Web-service
     * @throws MalformedURLException
     */
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