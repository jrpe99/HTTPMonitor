package dk.jrpe.monitor.db.mongodb.schema;


import com.mongodb.MongoClient;

import dk.jrpe.monitor.db.cassandra.CassandraConnectionHandler;

/**
 * Create schema and tables in a Cassandra database for HTTP access data.
 * @author Jörgen Persson
 *
 */
public class HTTPAccessSchema {
    public static void main(String[] args) {
        new HTTPAccessSchema().createSchema();
    }

    public void createSchema() {
        try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {
       } catch (Exception e) {
            System.out.println(e);
        }
    }
}