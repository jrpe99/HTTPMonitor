package dk.jrpe.monitor.source.httpaccess.simulate;

import java.io.File;
import java.net.URL;

import dk.jrpe.monitor.webservice.endpoint.generated.HTTPAccessDataPort;
import dk.jrpe.monitor.webservice.endpoint.generated.HTTPAccessDataService;

public class HTTPAccessDataServiceClient {
    protected HTTPAccessDataServiceClient() {
    }
    
    public static void main(String args[]) throws Exception {
        HTTPAccessDataService service;
        if (args.length != 0 && args[0].length() != 0) {
            File wsdlFile = new File(args[0]);
            URL wsdlURL;
            if (wsdlFile.exists()) {
                wsdlURL = wsdlFile.toURI().toURL();
            } else {
                wsdlURL = new URL(args[0]);
            }
            // Create the service client with specified wsdlurl
            service = new HTTPAccessDataService(wsdlURL);
        } else {
            // Create the service client with its default wsdlurl
        	service = new HTTPAccessDataService();
        }

        HTTPAccessDataPort httpAccessDataPort = service.getHTTPAccessDataPort();
        
        // Initialize the test class and call the tests
        HTTPAccessDataServiceTester client = new HTTPAccessDataServiceTester();
        client.setHTTPAccessDataService(httpAccessDataPort);
        client.testService();
        System.exit(0); 
    }
}
