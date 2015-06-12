package dk.jrpe.monitor.websocket.endpoint;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.ejb.EJB;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import dk.jrpe.monitor.service.EnterpriseMonitorService;
import dk.jrpe.monitor.service.MonitorService;
import dk.jrpe.monitor.service.StandardMonitorService;

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
     * Indicated is the service has been started.
     * The service is started when the first session 
     * is added.
     */
    private final AtomicBoolean serviceInitialized = new AtomicBoolean(false);
    

    private MonitorService monitorService;

    /**
     * Create the monitoring service when the WebSocket end-point is created.
     */
    @EJB
    private EnterpriseMonitorService enterpriseMonitorService;

    
    /**
     * When a new client connects, add the client session to the
     * monitoring service.
     * 
     * @param session
     */
    @OnOpen
    public void handleOpenConnection(Session session) {
        if(!this.serviceInitialized.getAndSet(true)) {
        	if(this.enterpriseMonitorService == null) {
        		this.monitorService = StandardMonitorService.getInstance();
        	} else {
        		this.monitorService = this.enterpriseMonitorService;
        	}
        	this.monitorService.start();
        }
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