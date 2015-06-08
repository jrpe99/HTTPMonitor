package dk.jrpe.monitor.webservice.cxf.endpoint;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;

import dk.jrpe.monitor.json.JSONMapper;
import dk.jrpe.monitor.service.command.CommandHandler;
import dk.jrpe.monitor.webservice.cxf.generated.endpoint.sync.HTTPAccessData;
import dk.jrpe.monitor.webservice.cxf.generated.endpoint.sync.HTTPAccessDataSyncPort;
/**
 * Implementation of a CXF generated web-service end-point.
 * <p>Receive HTTP Access data and convert the data to JSON 
 * which is the input format of the {@link dk.jrpe.monitor.service.command.CommandHandler}.
 * @author Jörgen Persson
 */
public class HTTPAccessDataSyncWSImpl implements HTTPAccessDataSyncPort {

    /**
     * The WebServiceContext can be used to retrieve special attributes like the 
     * user principal. Normally it is not needed
     */
    @Resource
    WebServiceContext wsContext;

	@Override
	public void sendHTTPAccessData(HTTPAccessData data) {
        CommandHandler.execute(JSONMapper.toJSON(data), null);
	}
}
