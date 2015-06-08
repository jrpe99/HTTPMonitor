package dk.jrpe.monitor.webservice.cxf.endpoint;

import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;
import javax.xml.ws.WebServiceContext;

import dk.jrpe.monitor.json.JSONMapper;
import dk.jrpe.monitor.service.command.CommandHandler;
import dk.jrpe.monitor.webservice.cxf.generated.endpoint.HTTPAccessData;
import dk.jrpe.monitor.webservice.cxf.generated.endpoint.HTTPAccessDataPort;
import dk.jrpe.monitor.webservice.cxf.generated.endpoint.SendHTTPAccessDataResponse;
/**
 * Implementation of a CXF web-service end-point with synchronous and asynchronous methods.
 * <p>NOTE, the method {@code public String sendHTTPAccessData(HTTPAccessData data)} is
 * used by CXF for both sync. and async. calls.
 * <p>Receive HTTP Access data and convert the data to JSON 
 * which is the input format of the {@link dk.jrpe.monitor.service.command.CommandHandler}.
 * @author Jörgen Persson
 */
public class HTTPAccessDataWSImpl implements HTTPAccessDataPort {
    /**
     * The WebServiceContext can be used to retrieve special attributes like the 
     * user principal. Normally it is not needed
     */
    @Resource
    WebServiceContext wsContext;

    /**
     * This method is used for both Sync. and Async. calls ...
     */
	@Override public String sendHTTPAccessData(HTTPAccessData data) {
        CommandHandler.execute(JSONMapper.toJSON(data), null);
		return "CXF Web-service operation finished";
	}

	@Override
	public Response<SendHTTPAccessDataResponse> sendHTTPAccessDataAsync(HTTPAccessData arg0) {
		return null;
	}

	@Override
	public Future<?> sendHTTPAccessDataAsync(HTTPAccessData data, AsyncHandler<SendHTTPAccessDataResponse> asyncHandler) {
		return null;
	}
}
