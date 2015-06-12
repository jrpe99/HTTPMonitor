package dk.jrpe.monitor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.Session;

import dk.jrpe.monitor.db.datasource.DataSourceFactory;
import dk.jrpe.monitor.db.datasource.HttpAccessDataSource;
import dk.jrpe.monitor.service.command.CommandHandler;
import dk.jrpe.monitor.task.HttpRequestsMonitorTask;
import dk.jrpe.monitor.task.HttpRequestsPerMinuteMonitorTask;
import dk.jrpe.monitor.task.MonitoringTask;

/**
 * <p> The method {@code addSession(Session)} is called when a new WebSocket session is opened.
 * 
 * <p> The method {@code removeSession(Session)} is called when a new WebSocket session is closed.
 * 
 * <p> The method {@code executeCommand(String, Session)} is called when a message is received form 
 * a WebSocket session.
 * 
 * <p> The method {@code start()} schedules all tasks using the {@code taskScheduler}.
 *  
 * @author Jörgen Persson
 */
public abstract class MonitorService {
    /**
     * Initialized in the method {@code start()}.
     */
    ScheduledExecutorService taskScheduler = null;

    /**
     * Contains all monitoring task, which are added in the constructor, 
     * and started in the method {@code start()}.
     */
    final List<MonitoringTask> monitoringTaskList = new ArrayList<>();
    
    /**
     * Contains all current WebSocket session. Sessions are added with
     * method {@code addSession(Session)} and removed with method 
     * {@code removeSession(Session)}. All monitoring task have a reference
     * to this list.
     */
    final List<Session> sessionList = new CopyOnWriteArrayList<>();

    /**
     * Get data source and construct monitoring tasks which are added to the
     * {@code monitoringTaskList}
     * <p> Start all tasks.
     */
    MonitorService() {
        HttpAccessDataSource dataSource = DataSourceFactory.get();
        this.monitoringTaskList.add(new HttpRequestsMonitorTask(dataSource, this.sessionList, 1000));
        this.monitoringTaskList.add(new HttpRequestsPerMinuteMonitorTask(dataSource, this.sessionList, 1000));
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
     * Stop the monitoring task and scheduler.
     */
    public void stop() {
    	this.taskScheduler.shutdown();
    	this.monitoringTaskList.stream().forEach((monitoringTask) -> {
            try {
                monitoringTask.cancel();
            } catch (Exception ex) {
                Logger.getLogger(MonitorService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * Schedule all monitoring tasks.
     */
    public abstract void start();
}