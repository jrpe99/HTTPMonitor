package dk.jrpe.monitor.task;

import dk.jrpe.monitor.db.datasource.HttpAccessDataSource;
import java.util.List;
import javax.websocket.Session;

/**
 * Parent of all monitoring tasks.
 * 
 * @author Jörgen Persson
 */
public abstract class MonitoringTask implements Runnable {

    private int delay = 1000;
    private List<Session> sessionList = null;

    private HttpAccessDataSource dataSource = null;

    MonitoringTask(HttpAccessDataSource dataSource, List<Session> sessionList, int delay) {
        this.sessionList = sessionList;
        this.delay = delay;
        this.dataSource = dataSource;
        this.dataSource.open();
    }
    
    public List<Session> getSessionList() {
        return sessionList;
    }

    public int getDelay() {
        return delay;
    }

    public HttpAccessDataSource getDataSouce() {
        return dataSource;
    }
    
    public void cancel() throws Exception {
        this.dataSource.close();
    }
}
