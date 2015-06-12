package dk.jrpe.monitor.service;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The service is a Singleton bean, which is used if the application 
 * is running in server not supporting full JEE7.
 * 
 * <p> See {@link dk.jrpe.monitor.service.MonitorService}
 * 
 * @author Jörgen Persson
 */
public final class StandardMonitorService extends MonitorService {
    private static class InstanceHolder {
        static final MonitorService instance = new StandardMonitorService();
    }

    /**
     * Return the instance of the monitoring service.
     * @return monitoring service instance
     */
    public static MonitorService getInstance() {
        return InstanceHolder.instance;
    }

    public void start() {
        this.taskScheduler = Executors.newScheduledThreadPool(monitoringTaskList.size());
        this.monitoringTaskList.stream().forEach((monitoringTask) -> {
        	this.taskScheduler.scheduleWithFixedDelay(monitoringTask, 0, monitoringTask.getDelay(), TimeUnit.MILLISECONDS);
        });
    }
}