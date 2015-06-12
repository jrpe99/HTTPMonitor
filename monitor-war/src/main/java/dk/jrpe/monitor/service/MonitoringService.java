package dk.jrpe.monitor.service;

import dk.jrpe.monitor.db.datasource.HttpAccessDataSource;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.websocket.Session;

/**
 * The service is a Singleton session bean. It is responsible for creating monitor tasks,
 * which are added to a monitor task list. When all tasks have been added
 * they are scheduled (started) using a ScheduledExecutorService.
 * 
 * <p> The method {@code addSession(Session)} is called when a new WebSocket session is opened.
 * 
 * <p> The method {@code removeSession(Session)} is called when a new WebSocket session is closed.
 * 
 * <p> The method {@code executeCommand(String, Session)} is called when a message is received form 
 * a WebSocket session.
 * 
 * @author Jörgen Persson
 */

@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
public class MonitoringService {
    /**
     * If the application is running in a full JEE7 container,
     * a managed scheduled executor will be used, which will
     * be injected by the container.
     */
    @Resource
    private ManagedScheduledExecutorService managedTaskScheduler;

    /**
     * Otherwise a non-managed will be used, which is initialized in the method {@code start()}.
     */
    private ScheduledExecutorService taskScheduler = null;

    /**
     * Indicated is the service has been started.
     * The service is started when the first session 
     * is added.
     */
    private final AtomicBoolean serviceStarted = new AtomicBoolean(false);
    
    /**
     * Contains all monitoring task, which are added in the constructor, 
     * and started in the method {@code start()}.
     */
    private final List<MonitoringTask> monitoringTaskList = new ArrayList<>();
    
    /**
     * Contains all current WebSocket session. Sessions are added with
     * method {@code addSession(Session)} and removed with method 
     * {@code removeSession(Session)}. All monitoring task have a reference
     * to this list.
     */
    private final List<Session> sessionList = new CopyOnWriteArrayList<>();

    /**
     * Get data source and construct monitoring tasks which are added to the
     * {@code monitoringTaskList}
     * <p> Start all tasks.
     */
    public MonitoringService() {
        HttpAccessDataSource dataSource = DataSourceFactory.get();
        this.monitoringTaskList.add(new HttpRequestsMonitorTask(dataSource, this.sessionList, 1000));
        this.monitoringTaskList.add(new HttpRequestsPerMinuteMonitorTask(dataSource, this.sessionList, 1000));
    }
    
    /**
     * Add a WebSocket session to each monitoring task.
     * If the monitoring tasks have not been scheduled (started),
     * then call method {@code start()}.
     * @param session the WebSocket session
     */
    public void addSession(Session session) {
        if(!this.serviceStarted.getAndSet(true)) {
            start();
        }
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
    	this.taskScheduler.shutdown();
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
     * If {@code managedTaskScheduler} is {@code null} the application is not running
     * in a full JEE7 container, like Tomcat 8.  
     */
    private void start() {
    	if(this.managedTaskScheduler != null) {
            this.taskScheduler = this.managedTaskScheduler;
    	} else {
            this.taskScheduler = Executors.newScheduledThreadPool(monitoringTaskList.size());
    	}
        this.monitoringTaskList.stream().forEach((monitoringTask) ->
                this.taskScheduler.scheduleWithFixedDelay(monitoringTask, 0, monitoringTask.getDelay(), TimeUnit.MILLISECONDS));
    }
}
