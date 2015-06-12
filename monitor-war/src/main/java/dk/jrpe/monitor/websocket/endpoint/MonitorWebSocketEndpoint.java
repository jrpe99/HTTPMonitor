package dk.jrpe.monitor.websocket.endpoint;

import dk.jrpe.monitor.service.MonitoringService;
import javax.ejb.EJB;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * WebSocket end-point for the monitoring service.
 * 
 * @author Jörgen Persson
 */
@ServerEndpoint(
    value = "/monitor"
)
public class MonitorWebSocketEndpoint {

    /**
     * Create the monitoring service when the WebSocket end-point is created.
     */
    @EJB
    private MonitoringService monitorService;
            
    /**
     * When a new client connects, add the client session to the
     * monitoring service.
     * 
     * @param session
     */
    @OnOpen
    public void handleOpenConnection(Session session) {
        this.monitorService.addSession(session);
    }

    /**
     * When a client disconnects, remove the client session from the
     * monitoring service.
     *
     * @param session
     * @param reason
     */
    @OnClose
    public void handleClosedConnection(Session session, CloseReason reason) {
        this.monitorService.removeSession(session);
    }

    /**
     * Handle commands sent from a client.
     * @param json
     * @param session
     */
    @OnMessage
    public void handleMessage(String json, Session session){
        this.monitorService.executeCommand(json, session);
    }
}