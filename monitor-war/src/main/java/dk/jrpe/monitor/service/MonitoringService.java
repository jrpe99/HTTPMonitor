package dk.jrpe.monitor.service;

import dk.jrpe.monitor.db.datasource.DataSource;
import dk.jrpe.monitor.db.datasource.DataSourceFactory;
import dk.jrpe.monitor.service.command.CommandHandler;
import dk.jrpe.monitor.task.HttpRequestsMonitorTask;
import dk.jrpe.monitor.task.HttpRequestsPerMinuteMonitorTask;
import dk.jrpe.monitor.task.MonitoringTask;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

/**
 * General purpose monitoring service.
 * 
 * The service is a Singelton. It is responsible for creating monitor tasks,
 * which are added to a monitor task list. When all tasks have been added
 * they are scheduled (started) using a ScheduledExecutorService.
 * 
 * @author Jörgen Persson
 */
public class MonitoringService {
    private static class InstanceHolder {
        static final MonitoringService instance = new MonitoringService();
    }
    
    private ScheduledExecutorService monitorTimer = null;
    
    private final List<MonitoringTask> monitoringTaskList = new ArrayList<>();
    private final List<Session> sessionList = new CopyOnWriteArrayList<>();

    /**
     * Get data source and construct monitoring tasks which are added to the
     * {@code monitoringTaskList}
     * <p> Start all tasks.
     */
    private MonitoringService() {
        try {
            DataSource dataSource = DataSourceFactory.getDefault();
            this.monitoringTaskList.add(new HttpRequestsMonitorTask(dataSource, this.sessionList, 1000));
            this.monitoringTaskList.add(new HttpRequestsPerMinuteMonitorTask(dataSource, this.sessionList, 1000));
            start();
        } catch (Exception e) {
            Logger.getLogger(MonitoringService.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * Return the instance of the monitoring service.
     * @return monitoring service instance
     */
    public static MonitoringService getInstance() {
        return InstanceHolder.instance;
    }
    
    /**
     * Add a WebSocket session to each monitoring task.
     * 
     * @param session the WebSocket session
     */
    public void addSession(Session session) {
    	this.sessionList.add(session);
    }
    
    /**
     * Remove a WebSocket session from each monitoring task.
     *
     * @param session the WebSocket session
     */
    public void removeSession(Session session) {
    	this.sessionList.remove(session);
    }

    /**
     * Execute the command sent from the client
     * @param json the json string representing command and data.
     * @param session the WebSocket session
     */
    public void executeCommand(String json, Session session) {
        CommandHandler.execute(json, session);
    }
    /**
     * Stop the monitoring timer.
     */
    public void stop() {
    	this.monitorTimer.shutdown();
    	this.monitoringTaskList.stream().forEach((monitoringTask) -> {
            try {
                monitoringTask.cancel();
            } catch (Exception ex) {
                Logger.getLogger(MonitoringService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * Schedule all monitoring tasks.
     */
    private void start() {
        this.monitorTimer = Executors.newScheduledThreadPool(monitoringTaskList.size());
        this.monitoringTaskList.stream().forEach((monitoringTask) -> {
        	this.monitorTimer.scheduleWithFixedDelay(monitoringTask, 0, monitoringTask.getDelay(), TimeUnit.MILLISECONDS);
        });
    }
}
