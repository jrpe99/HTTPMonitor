package dk.jrpe.monitor.webservice.cxf.async;

import java.util.Random;

import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;

import dk.jrpe.monitor.webservice.cxf.generated.endpoint.SendHTTPAccessDataResponse;

/**
 * 
 * @author Jörgen Persson
 *
 */
public class WebServiceAsyncHandler implements AsyncHandler<SendHTTPAccessDataResponse> {
    
    private SendHTTPAccessDataResponse response;
    private final Random random = new Random();
    
    public void handleResponse(Response<SendHTTPAccessDataResponse> response) {
        try {
        	/*
        	 * Simulate a delay in the async. call
        	 */
        	Thread.sleep(random.nextInt(3000));
            this.response = response.get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String getStatus() {
        return this.response.getStatus();
    }
}
