package dk.jrpe.monitor.source.httpaccess.simulate;

import java.util.Random;

import dk.jrpe.monitor.source.httpaccess.client.CassandraClient;
import dk.jrpe.monitor.source.httpaccess.to.HTTPAccessTOFactory;

public class SimulatorCassandraClient {
    public static void main(String[] args) {
        try {
            Random random = new Random();
            CassandraClient client = new CassandraClient();
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