package dk.jrpe.monitor.service;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;

/**
 * The service is a Singleton session bean. Used when the application is running in 
 * a full JEE7 server.
 * 
 * <p> See {@link dk.jrpe.monitor.service.MonitorService}
 * 
 * @author Jörgen Persson
 */

@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
public class EnterpriseMonitorService extends MonitorService {
    /**
     * A managed scheduled executor be injected by the container.
     */
    @Resource
    private ManagedScheduledExecutorService managedTaskScheduler;

    /**
     * Get data source and construct monitoring tasks which are added to the
     * {@code monitoringTaskList}
     * <p> Start all tasks.
     */
    
    public void start() {
        this.monitoringTaskList.stream().forEach((monitoringTask) ->
                this.managedTaskScheduler.scheduleWithFixedDelay(monitoringTask, 0, monitoringTask.getDelay(), TimeUnit.MILLISECONDS));
    }
}
