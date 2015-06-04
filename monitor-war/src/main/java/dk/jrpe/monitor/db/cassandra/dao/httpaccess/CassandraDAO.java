package dk.jrpe.monitor.db.cassandra.dao.httpaccess;

import dk.jrpe.monitor.db.cassandra.CassandraConnectionHandler;

/**
 * Parent for Cassandra Data Access Object implementations
 * @author Jörgen Persson
 *
 */
public class CassandraDAO {

    CassandraConnectionHandler conn = null;
    
    /**
     * Construct a DAO object using the Cassandra connection handler.
     * @param conn
     */
    public CassandraDAO(CassandraConnectionHandler conn) {
        this.conn = conn;
    }
}
