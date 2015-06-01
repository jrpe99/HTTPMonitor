package dk.jrpe.monitor.webservice.endpoint;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;

import dk.jrpe.monitor.db.httpaccess.to.JsonHTTPAccessTO;
import dk.jrpe.monitor.json.JSONMapper;
import dk.jrpe.monitor.service.command.CommandHandler;
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
        JsonHTTPAccessTO jsonTo = new JsonHTTPAccessTO(data);
        CommandHandler.execute(JSONMapper.toJSON(jsonTo), null);
	}
}
