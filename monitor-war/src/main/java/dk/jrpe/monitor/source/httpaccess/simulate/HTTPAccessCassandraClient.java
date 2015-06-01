package dk.jrpe.monitor.source.httpaccess.simulate;

import java.util.Random;

import dk.jrpe.monitor.db.cassandra.dao.httpaccess.HTTPAccessDAO;
import dk.jrpe.monitor.db.datasource.DataSource;
import dk.jrpe.monitor.db.datasource.DataSourceFactory;
import dk.jrpe.monitor.source.httpaccess.to.HTTPAccessTOFactory;

public class HTTPAccessCassandraClient {
    private final DataSource dataSource = DataSourceFactory.getDefault();
    public static void main(String[] args) {
        new HTTPAccessCassandraClient().simulateWithCassandra();
    }

    public void simulateWithCassandra() {
        this.dataSource.open();
        try (DataSource localDataSource = this.dataSource;) {
            Random random = new Random();
            HTTPAccessDAO httpAccessDAO = new HTTPAccessDAO(localDataSource);
            while(true) {
                httpAccessDAO.saveAndUpdate(HTTPAccessTOFactory.createSimulated());
                
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