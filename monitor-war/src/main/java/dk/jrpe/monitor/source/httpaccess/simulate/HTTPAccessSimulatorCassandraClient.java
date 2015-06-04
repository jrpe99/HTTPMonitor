package dk.jrpe.monitor.source.httpaccess.simulate;

import java.util.Random;

import dk.jrpe.monitor.source.httpaccess.client.HTTPAccessCassandraClient;

public class HTTPAccessSimulatorCassandraClient {
    public static void main(String[] args) {
        try {
            Random random = new Random();
            HTTPAccessCassandraClient client = new HTTPAccessCassandraClient();
            while(true) {
                client.saveAndUpdate(HTTPAccessTOFactory.createSimulated());
                
                int sleepTime = random.nextInt(500);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}