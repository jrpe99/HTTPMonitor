package dk.jrpe.monitor.source.httpaccess.simulate;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import dk.jrpe.monitor.db.httpaccess.to.JsonHTTPAccessTO;
import dk.jrpe.monitor.webservice.endpoint.generated.HTTPAccessData;

/**
 *
 * @author Jörgen Persson
 */
public class HTTPAccessTOFactory {

    private HTTPAccessTOFactory() {
    }

    public static HTTPAccessData convertToCXFObject(HTTPAccessTO to) {
    	HTTPAccessData httpAccessData = new HTTPAccessData();
    	httpAccessData.setAction(to.getAction());
    	httpAccessData.setDate(to.getDate());
    	httpAccessData.setDateTime(to.getDateTime());
    	httpAccessData.setDateToMinute(to.getDateToMinute());
    	httpAccessData.setHttpStatus(to.getHttpStatus());
    	httpAccessData.setIpAddress(to.getIpAddress());
    	httpAccessData.setRequests(to.getRequests());
    	httpAccessData.setUrl(to.getUrl());
    	return httpAccessData;
    }
    
    public static JsonHTTPAccessTO convertToJsonTo(HTTPAccessTO to) {
    	JsonHTTPAccessTO jsonHTTPAccessTO = new JsonHTTPAccessTO();
    	jsonHTTPAccessTO.setAction(to.getAction());
    	jsonHTTPAccessTO.setDate(to.getDate());
    	jsonHTTPAccessTO.setDateTime(to.getDate());
    	jsonHTTPAccessTO.setDateToMinute(to.getDateToMinute());
    	jsonHTTPAccessTO.setHttpStatus(to.getHttpStatus());
    	jsonHTTPAccessTO.setIpAddress(to.getIpAddress());
    	jsonHTTPAccessTO.setRequests(to.getRequests());
    	jsonHTTPAccessTO.setUrl(to.getUrl());
    	return jsonHTTPAccessTO;
    }
    
    public static JsonHTTPAccessTO convertToJsonTo(HTTPAccessData to) {
    	JsonHTTPAccessTO jsonHTTPAccessTO = new JsonHTTPAccessTO();
    	jsonHTTPAccessTO.setCommand(to.getCommand());
    	jsonHTTPAccessTO.setAction(to.getAction());
    	jsonHTTPAccessTO.setDate(to.getDate());
    	jsonHTTPAccessTO.setDateTime(to.getDate());
    	jsonHTTPAccessTO.setDateToMinute(to.getDateToMinute());
    	jsonHTTPAccessTO.setHttpStatus(to.getHttpStatus());
    	jsonHTTPAccessTO.setIpAddress(to.getIpAddress());
    	jsonHTTPAccessTO.setRequests(to.getRequests());
    	jsonHTTPAccessTO.setUrl(to.getUrl());
    	return jsonHTTPAccessTO;
    }
    
    public static HTTPAccessTO convertToDbTo(JsonHTTPAccessTO to) {
        return new HTTPAccessTO.Builder()
	        .setIPAdress(to.getIpAddress())
	        .setHttpStatus(to.getHttpStatus())
	        .setDate(to.getDate())
	        .setDateToMinute(to.getDateToMinute())
	        .setDateTime(to.getDateTime())
	        .setAction(to.getAction())
	        .setUrl(to.getUrl())
	        .setRequests(to.getRequests())
	        .build();    	
    }
    
    public static HTTPAccessTO createSimulated() {
        Random random = new Random();
        int index = random.nextInt(SimulationConstants.HTTP_STATUS_LIST.size());
        String httpStatus = SimulationConstants.HTTP_STATUS_LIST.get(index);
        
        index = random.nextInt(9) * 10;
        String ipAddress = "1" + index + ".";
        index = random.nextInt(9) * 10;
        ipAddress = ipAddress + "132.100." + "2" + index;
        
        index = random.nextInt(SimulationConstants.ACTION_LIST.size());
        String action = SimulationConstants.ACTION_LIST.get(index);
        
        index = random.nextInt(SimulationConstants.URL_LIST.size());
        String url = SimulationConstants.URL_LIST.get(index);
        
        ZonedDateTime now = ZonedDateTime.now().withSecond(0).withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String date = formatter.format(now);
            
        formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String dateToMinute = formatter.format(now);
        
        now = ZonedDateTime.now();
        
        //httpStatus, ipAddress, date, dateToMinute
        return new HTTPAccessTO.Builder()
                .setIPAdress(ipAddress)
                .setHttpStatus(httpStatus)
                .setDate(date)
                .setDateToMinute(dateToMinute)
                .setDateTime(""+now.toEpochSecond())
                .setAction(action)
                .setUrl(url)
                .build();
    }
}
