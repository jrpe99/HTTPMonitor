package dk.jrpe.monitor.source.httpaccess.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Future;

import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import dk.jrpe.monitor.source.httpaccess.to.HTTPAccessTOFactory;
import dk.jrpe.monitor.webservice.cxf.CXFWebServiceMode;
import dk.jrpe.monitor.webservice.cxf.async.WebServiceAsyncHandler;
import dk.jrpe.monitor.webservice.cxf.generated.endpoint.HTTPAccessData;
import dk.jrpe.monitor.webservice.cxf.generated.endpoint.HTTPAccessDataPort;
import dk.jrpe.monitor.webservice.cxf.generated.endpoint.HTTPAccessDataService;

/**
 * Client for sending HTTP access data to the server using a CXF Web-service.
 * 
 * @author Jörgen Persson
 *
 */
public class CXFWebServiceClient extends ServiceClient {

    private final HTTPAccessDataPort httpAccessDataPort;
    private final CXFWebServiceMode mode;
    /**
     * Initialize the Web-service
     * @throws MalformedURLException
     */
	public CXFWebServiceClient(CXFWebServiceMode mode) throws MalformedURLException {
        URL wsdlURL = new URL("http://localhost:8080/monitor/services/HTTPAccessServicePort?WSDL");
		this.httpAccessDataPort = new HTTPAccessDataService(wsdlURL).getHTTPAccessDataPort();
		this.mode = mode;
	}
	
	public void sendToServer(HTTPAccessTO to, String command) throws Exception {
		if(this.mode == CXFWebServiceMode.SYNC) {
			sendToServerSync(to, command);
		} else if(this.mode == CXFWebServiceMode.ASYNC) {
			sendToServerAsync(to, command);
		}
	}

	private void sendToServerSync(HTTPAccessTO to, String command) throws Exception {
    	HTTPAccessData data = HTTPAccessTOFactory.convertToCXFObject(to);
    	data.setCommand(command);
    	httpAccessDataPort.sendHTTPAccessData(data);
		System.out.println(this.getClass().getName() + " sendToServerSync - Finished");
	}

	private void sendToServerAsync(HTTPAccessTO to, String command) throws Exception {
    	HTTPAccessData data = HTTPAccessTOFactory.convertToCXFObject(to);
    	data.setCommand(command);
    	WebServiceAsyncHandler webServiceAsyncHandler = new WebServiceAsyncHandler();
    	Future<?> response = httpAccessDataPort.sendHTTPAccessDataAsync(data, webServiceAsyncHandler);
    	while(!response.isDone()) {
    		System.out.println(this.getClass().getName() + " sendToServerAsync - WAIT");
    		Thread.sleep(100);
    	}
		System.out.println(this.getClass().getName() + " sendToServerAsync - Finished");
		System.out.println(this.getClass().getName() + " sendToServerAsync - response from server : " + webServiceAsyncHandler.getStatus());
	}
}