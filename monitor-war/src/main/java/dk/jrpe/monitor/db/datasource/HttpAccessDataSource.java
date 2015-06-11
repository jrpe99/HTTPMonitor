package dk.jrpe.monitor.db.datasource;

import java.util.List;

import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;

/**
 * Data source for HTTP Access data 
 * @author Jörgen Persson
 */
public interface HttpAccessDataSource extends AutoCloseable {
	/**
	 * Get all successful request. 
	 * @return a list of {@link dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO} objects. 
	 */
    public List<HTTPAccessTO> getHttpSuccess();

    /**
	 * Get all failed request. 
	 * @return a list of {@link dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO} objects. 
	 */
    public List<HTTPAccessTO> getHttpFailed();

    /**
     * Return a list of objects, each representing number of successful HTTP accesses 
     * during one minute. 
     * @param date the date for the given interval
     * @param from start of the interval. Must be within the given date and formated as DateTimeFormatter.ISO_LOCAL_DATE_TIME
     * @param to end of the interval. Must be within the given date and formated as DateTimeFormatter.ISO_LOCAL_DATE_TIME
	 * @return a list of {@link dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO} objects. 
	 * Each object represents number of successful HTTP access for a given minute.  
     */
    public List<HTTPAccessTO> getHttpSuccessPerMinute(String date, String from, String to);

    /**
     * Return a list of objects, each representing number of failed HTTP accesses 
     * during one minute. 
     * @param date the date for the given interval
     * @param from start of the interval. Must be within the given date and formated as DateTimeFormatter.ISO_LOCAL_DATE_TIME
     * @param to end of the interval. Must be within the given date and formated as DateTimeFormatter.ISO_LOCAL_DATE_TIME
	 * @return a list of {@link dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO} objects. 
	 * Each object represents number of failed HTTP accesses for a given minute.  
     */
    public List<HTTPAccessTO> getHttpFailedPerMinute(String date, String from, String to);

    /**
     * Save the object in the database.
     * @param to object to be saved. 
     * @param hour which hour of the day the HTTP access was made.
     */
    public void saveHttpAccess(HTTPAccessTO to, int hour);
    
    /**
     * Update number of successful request for the given IP address.
     * @param to the object representing one successful request for the given IP address. 
     */
    public void updateHttpSuccess(HTTPAccessTO to);
    
    /**
     * Update number of successful request for the given IP address within the current minute.
     * @param to the object representing one successful request for the given IP address during current minute.  
     */
    public void updateHttpSuccessPerMinute(HTTPAccessTO to);

    /**
     * Update number of failed request for the given IP address.
     * @param to the object representing one failed request for the given IP address. 
     */
    public void updateHttpFailed(HTTPAccessTO to);

    /**
     * Update number of failed request for the given IP address within the current minute.
     * @param to the object representing one failed request for the given IP address during current minute.  
     */
    public void updateHttpFailedPerMinute(HTTPAccessTO to);
    
    /**
     * Make a connection to the database.
     */
    public void open();
}
