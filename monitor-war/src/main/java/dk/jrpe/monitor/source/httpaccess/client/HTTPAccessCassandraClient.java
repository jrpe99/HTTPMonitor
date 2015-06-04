package dk.jrpe.monitor.source.httpaccess.client;

import dk.jrpe.monitor.db.cassandra.dao.httpaccess.HTTPAccessDAO;
import dk.jrpe.monitor.db.datasource.DataSource;
import dk.jrpe.monitor.db.datasource.DataSourceFactory;
import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;

/**
 * Client for saving and updating HTTP access data in a Cassandra database.
 * @author Jörgen Persson
 *
 */
public class HTTPAccessCassandraClient {
    private final DataSource dataSource = DataSourceFactory.getDefault();
    
    /**
     * Save and update the Cassandra database.
     * @param to data to save/update the database with
     * @throws Exception if not possible to connect to the database.
     */
	public void saveAndUpdate(HTTPAccessTO to) throws Exception {
        try (DataSource localDataSource = this.dataSource;) {
            HTTPAccessDAO httpAccessDAO = new HTTPAccessDAO(localDataSource);
            httpAccessDAO.saveAndUpdate(to);
        }
    }
}