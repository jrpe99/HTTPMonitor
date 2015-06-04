package dk.jrpe.monitor.db.cassandra.dao.httpaccess;

import dk.jrpe.monitor.db.datasource.DataSource;
import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import java.time.ZonedDateTime;

/**
 * Responsible for updating HTTP access data.
 * 
 * <p> The constructor takes desired data source, which is used when updating data.
 * 
 * <p> The method {@code saveAndUpdate} is used to update HTTP access data.
 * @author Jörgen Persson
 */
public class HTTPAccessDAO {

    private DataSource dataSource = null;
    
    public HTTPAccessDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * Updates multiple tables using the supplied object.
     * <br> - Save the HTTP access object
     * <br> - Update number of successful requests for the IP address.
     * <br> - Update number of successful requests per minute.
     * <br> - Update number of failed requests for the IP address.
     * <br> - Update number of failed requests per minute.
     * <p> Successful is only HTTP status 200.
     * @param to object representing a request from an IP address.
     */
    public void saveAndUpdate(HTTPAccessTO to) {
        System.out.println("Save: " + to.toString());
        ZonedDateTime now = ZonedDateTime.now().withSecond(0).withNano(0);
        int hour = now.getHour();

        this.dataSource.saveHttpAccess(to, hour);

        if(to.getHttpStatus().equals("200")) {
            this.dataSource.updateHttpSuccess(to);
            this.dataSource.updateHttpSuccessPerMinute(to);
        } else {
            this.dataSource.updateHttpFailed(to);
            this.dataSource.updateHttpFailedPerMinute(to);
        }
    }
}
