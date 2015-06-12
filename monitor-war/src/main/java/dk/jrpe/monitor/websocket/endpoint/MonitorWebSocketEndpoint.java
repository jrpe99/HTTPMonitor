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
 * <p> The method {@code handleOpenConnection(Session)} checks if the monitor service 
 * {@code monitorService} has been initialized. If not, it will use either an
 * enterprise service for a full JEE7 server (like Glassfish 4.1) or a standard service for a server not
 * supporting full JEE7 (like Tomcat 8).
 * 
 * @author Jörgen Persson
 */
@ServerEndpoint(value = "/monitor")
public class MonitorWebSocketEndpoint {
	/**
	 * Indicated is the service has been started. The service is started when
	 * the first session is added.
	 */
	private static final AtomicBoolean serviceInitialized = new AtomicBoolean(false);

	private static MonitorService monitorService;

	/**
	 * Inject the monitoring service (Singleton) when the WebSocket end-point is
	 * created, if the application is running in server supporting full JEE7.
	 */
	@EJB
	private EnterpriseMonitorService enterpriseMonitorService;

	/**
	 * Synchronized on {@code serviceInitialized}.
	 * <br>
	 * When a new client connects, add the client session to the monitoring service.
	 * If the the monitoring service is not initialized then initialized the 
	 * proper service.
	 * 
	 * @param session
	 */
	@OnOpen
	public void handleOpenConnection(Session session) {
		synchronized (serviceInitialized) {
			if (!serviceInitialized.getAndSet(true)) {
				if (enterpriseMonitorService == null) {
					monitorService = StandardMonitorService.getInstance();
				} else {
					monitorService = enterpriseMonitorService;
				}
				monitorService.start();
			}
			monitorService.addSession(session);
		}
	}

	/**
	 * When a client disconnects, remove the client session from the monitoring
	 * service.
	 *
	 * @param session
	 * @param reason
	 */
	@OnClose
	public void handleClosedConnection(Session session, CloseReason reason) {
		monitorService.removeSession(session);
	}

	/**
	 * Handle commands sent from a client.
	 * 
	 * @param json
	 * @param session
	 */
	@OnMessage
	public void handleMessage(String json, Session session) {
		monitorService.executeCommand(json, session);
	}
}