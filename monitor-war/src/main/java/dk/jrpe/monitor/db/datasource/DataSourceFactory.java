package dk.jrpe.monitor.db.datasource;

import dk.jrpe.monitor.db.datasource.cassandra.CassandraDataSource;
import dk.jrpe.monitor.db.datasource.inmemory.InMemoryDataSource;

/**
 * Factory Enum for data sources. Default data source is IN_MEMORY.
 * @author Jörgen Persson
 */
public enum DataSourceFactory {
    CASSANDRA(new CassandraDataSource()), IN_MEMORY(new InMemoryDataSource());
    
    private final HttpAccessDataSource dataSource;
    DataSourceFactory(HttpAccessDataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * Get default data source.
     * @return the instance of the default data source.
     */
    public static HttpAccessDataSource getDefault() {
//        return CASSANDRA.get();
        return IN_MEMORY.get();
    }
    
    /**
     * Get current data source
     * @return the instance of current data source.
     */
    public HttpAccessDataSource get() {
        return this.dataSource;
    }
}
