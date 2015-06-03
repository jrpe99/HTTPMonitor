package dk.jrpe.monitor.source.httpaccess.client;

import dk.jrpe.monitor.db.cassandra.dao.httpaccess.HTTPAccessDAO;
import dk.jrpe.monitor.db.datasource.DataSource;
import dk.jrpe.monitor.db.datasource.DataSourceFactory;
import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;

public class HTTPAccessCassandraClient {
    private final DataSource dataSource = DataSourceFactory.getDefault();
    
	public void sendToServer(HTTPAccessTO to) throws Exception {
        try (DataSource localDataSource = this.dataSource;) {
            HTTPAccessDAO httpAccessDAO = new HTTPAccessDAO(localDataSource);
            httpAccessDAO.saveAndUpdate(to);
        }
    }
}