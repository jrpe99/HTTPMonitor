package dk.jrpe.monitor.db.mongodb;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

/**
 * Connection handler for a Cassandra database.
 * <p> A connection is created during construction of the handler.
 * 
 * <p> The {@code execute} method is used to execute CQL statements using the 
 * session created by the {@code connect} method.
 * 
 * <p> The method {@code close} must be called after the application is finished
 * executing.
 *
 * @author Jörgen Persson
 *
 */
public class MongoDBConnectionHandler implements AutoCloseable {

    private Cluster cluster;
    private Session session;

    
    public MongoDBConnectionHandler() {
    	connect();
    }

    /**
     * Get current session.
     * @return current session
     */
	public Session getSession() {
        return this.session;
    }

	/**
	 * Connect to the Cassandra database and set instance variables {@code cluster} and {@code session}
	 */
    private void connect() {
        Builder builder = Cluster.builder();
        builder.addContactPoint("192.168.15.130");
        //builder.addContactPoint("192.168.15.131");
        //builder.addContactPoint("192.168.15.132");
        this.cluster = builder.build();
        
        Metadata metadata = cluster.getMetadata();
        System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
        for (Host host : metadata.getAllHosts()) {
            System.out.printf("Datatacenter: %s; Host: %s; Rack: %s\n", host.getDatacenter(), host.getAddress(),
                    host.getRack());
        }
        this.session = cluster.connect();
    }

    /**
     * Execute the given CQL
     * @param cql CQL to be executed.
     * @return
     */
    public ResultSet execute(String cql) {
        return this.session.execute(cql);
    }
    
    @Override public void close() {
        if (this.session != null) {
        	this.session.close();
        }
        
        if (this.cluster != null) {
        	this.cluster.close();
        }
    }
}