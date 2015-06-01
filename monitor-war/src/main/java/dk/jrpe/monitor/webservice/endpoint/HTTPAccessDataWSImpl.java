package dk.jrpe.monitor.webservice.endpoint;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;

import dk.jrpe.monitor.webservice.endpoint.generated.HTTPAccessData;
import dk.jrpe.monitor.webservice.endpoint.generated.HTTPAccessDataPort;
/**
 *
 * @author Jörgen Persson
 */
public class HTTPAccessDataWSImpl implements HTTPAccessDataPort {

    /**
     * The WebServiceContext can be used to retrieve special attributes like the 
     * user principal. Normally it is not needed
     */
    @Resource
    WebServiceContext wsContext;

	@Override
	public void sendHTTPAccessData(HTTPAccessData data) {
		System.out.println(data.toString());
	}
}
