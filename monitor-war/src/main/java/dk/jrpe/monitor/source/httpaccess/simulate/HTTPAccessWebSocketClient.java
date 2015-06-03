package dk.jrpe.monitor.source.httpaccess.simulate;

import java.util.Random;

import dk.jrpe.monitor.db.cassandra.dao.httpaccess.HTTPAccessDAO;
import dk.jrpe.monitor.db.datasource.DataSource;
import dk.jrpe.monitor.db.datasource.DataSourceFactory;
import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import dk.jrpe.monitor.db.httpaccess.to.JsonHTTPAccessTO;
import dk.jrpe.monitor.json.JSONMapper;
import dk.jrpe.monitor.service.command.CommandHandler;
import dk.jrpe.monitor.webservice.endpoint.generated.HTTPAccessData;
import dk.jrpe.monitor.webservice.endpoint.generated.HTTPAccessDataPort;
import dk.jrpe.monitor.webservice.endpoint.generated.HTTPAccessDataService;
import dk.jrpe.monitor.websocket.client.WebsocketClientEndpoint;

import java.net.URI;
import java.net.URL;

public class HTTPAccessWebSocketClient {
    
	public static void main(String[] args) {
        new HTTPAccessWebSocketClient().simulateWithWebsocket();
    }

    public void simulateWithWebsocket() {
        try {
            WebsocketClientEndpoint client = new WebsocketClientEndpoint(new URI("ws://localhost:8080/monitor/monitor"));
            Random random = new Random();
            while (true) {
                HTTPAccessTO to = HTTPAccessTOFactory.createSimulated();
                JsonHTTPAccessTO jsonTo = new JsonHTTPAccessTO(to);
                if (to.getHttpStatus().equals("200")) {
                    jsonTo.setCommand(CommandHandler.CommandEnum.SEND_HTTP_SUCCESS_DATA.toString());
                    client.send(JSONMapper.toJSON(jsonTo));
                    jsonTo.setCommand(CommandHandler.CommandEnum.SEND_HTTP_SUCCESS_PER_MINUTE_DATA.toString());
                    client.send(JSONMapper.toJSON(jsonTo));
                } else {
                    jsonTo.setCommand(CommandHandler.CommandEnum.SEND_HTTP_FAILED_DATA.toString());
                    client.send(JSONMapper.toJSON(jsonTo));
                    jsonTo.setCommand(CommandHandler.CommandEnum.SEND_HTTP_FAILED_PER_MINUTE_DATA.toString());
                    client.send(JSONMapper.toJSON(jsonTo));
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