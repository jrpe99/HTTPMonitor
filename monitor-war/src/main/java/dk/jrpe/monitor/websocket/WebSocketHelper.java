package dk.jrpe.monitor.websocket;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.Session;

import dk.jrpe.monitor.service.MonitorConstant;
import dk.jrpe.monitor.service.chart.ChartEnum;

/**
 * Used to send charts to the clients. Only charts subscribed by the client are sent.
 * See {@link dk.jrpe.monitor.service.chart.ChartSubscriptionHandler}
 * @author Jörgen Persson
 */
public class WebSocketHelper {
    
    public static void sendChartListToAll(List<Session> sessionList, final ChartEnum ... chartList) {
        if(sessionList.size() > 0) {
            sessionList.stream().forEach((session) -> {
                EnumSet chartSubscription = (EnumSet)session.getUserProperties().get(MonitorConstant.CHART_SUBSCRIPTION);
                for (ChartEnum chart : chartList) {
                    if(chartSubscription != null && chartSubscription.contains(chart)) {
                        WebSocketHelper.send(session, chart.getJson());
                    }
                }
            });
        }
    }
    
    public static void send(Session session, String json) {
        try {
            System.out.println("Send to session : " + session.getId());
            System.out.println("Message : " + json);
            session.getBasicRemote().sendText(json);
        } catch (IOException ex) {
            Logger.getLogger(WebSocketHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
