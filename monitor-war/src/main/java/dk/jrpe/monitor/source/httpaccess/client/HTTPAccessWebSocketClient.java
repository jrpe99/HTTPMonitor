package dk.jrpe.monitor.source.httpaccess.client;

import java.net.URI;
import java.net.URISyntaxException;

import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import dk.jrpe.monitor.db.httpaccess.to.JsonHTTPAccessTO;
import dk.jrpe.monitor.json.JSONMapper;
import dk.jrpe.monitor.source.httpaccess.simulate.HTTPAccessTOFactory;
import dk.jrpe.monitor.websocket.client.WebsocketClientEndpoint;

public class HTTPAccessWebSocketClient extends HTTPAccessWebClient {

    private final WebsocketClientEndpoint client;
    
	public HTTPAccessWebSocketClient() throws URISyntaxException {
		this.client = new WebsocketClientEndpoint(new URI("ws://localhost:8080/monitor/monitor"));
	}
	
	public void sendToServer(HTTPAccessTO to, String command) throws Exception {
        JsonHTTPAccessTO jsonTo = HTTPAccessTOFactory.convertToJsonTo(to);
        jsonTo.setCommand(command);
        this.client.send(JSONMapper.toJSON(jsonTo));
	}
}