package dk.jrpe.monitor.source.httpaccess.client;

import java.net.MalformedURLException;
import java.net.URL;

import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import dk.jrpe.monitor.source.httpaccess.to.HTTPAccessTOFactory;
import dk.jrpe.monitor.webservice.cxf.generated.endpoint.sync.HTTPAccessData;
import dk.jrpe.monitor.webservice.cxf.generated.endpoint.sync.HTTPAccessDataSyncPort;
import dk.jrpe.monitor.webservice.cxf.generated.endpoint.sync.HTTPAccessDataSyncService;

/**
 * Client for sending HTTP access data to the server using a CXF Web-service.
 * 
 * @author Jörgen Persson
 *
 */
public class CXFSyncWebServiceClient extends ServiceClient {

    private final HTTPAccessDataSyncPort httpAccessDataPort;

    /**
     * Initialize the Web-service
     * @throws MalformedURLException
     */
	public CXFSyncWebServiceClient() throws MalformedURLException {
        URL wsdlURL = new URL("http://localhost:8080/monitor/services/HTTPAccessServiceSyncPort?WSDL");
		httpAccessDataPort = new HTTPAccessDataSyncService(wsdlURL).getHTTPAccessDataSyncPort();
	}

	public void sendToServer(HTTPAccessTO to, String command) throws Exception {
    	HTTPAccessData data = HTTPAccessTOFactory.convertToCXFObject(to);
    	data.setCommand(command);
    	httpAccessDataPort.sendHTTPAccessData(data);
	}
}