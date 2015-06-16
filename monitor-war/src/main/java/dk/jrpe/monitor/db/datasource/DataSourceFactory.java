package dk.jrpe.monitor.db.datasource;

import dk.jrpe.monitor.db.datasource.cassandra.CassandraDataSource;
import dk.jrpe.monitor.db.datasource.inmemory.InMemoryDataSource;
import dk.jrpe.monitor.db.datasource.mongodb.MongoDBDataSource;

/**
 * Singelton.
 * Factory class for data-sources. A default data-source is initialized
 * when the singelton initializes.
 * 
 * <p> Method {@code get()} will return the initialized data-source.
 * <p> Method {@code set(DataSourceFactory.Type source)} is used for JUnit and not to dynamically change the data-source.
 * @author Jörgen Persson
 *
 */
public final class DataSourceFactory {
	private HttpAccessDataSource dataSource;
	
    private static class InstanceHolder {
        static final DataSourceFactory instance = new DataSourceFactory();
    }

    /**
     * Set default data-source
     */
	private DataSourceFactory() {
		/*
		 * Init possibly from property file
		 */
		this.dataSource = Type.MONGODB.get();
		//this.dataSource = Type.IN_MEMORY.get();
		//this.dataSource = Type.CASSANDRA.get();
	}

	/**
	 * Get default HTTP access data-source
	 * @return default HTTP access data-source
	 */
	public static HttpAccessDataSource get() {
		return InstanceHolder.instance.dataSource;
	}

	/**
	 * Set data-source (override the default)
	 * NOTE, used for JUnit and not to dynamically change the data-source.
	 * @param source
	 */
	public static void set(DataSourceFactory.Type source) {
		InstanceHolder.instance.dataSource = source.get();
	}
	
	/**
	 * Enumeration containing all possible data-sources for HTTP access data 
	 */
	public enum Type {
	    CASSANDRA(new CassandraDataSource()), 
	    IN_MEMORY(new InMemoryDataSource()),
	    MONGODB(new MongoDBDataSource());
	    
	    private final HttpAccessDataSource dataSource;
	    Type(HttpAccessDataSource dataSource) {
	        this.dataSource = dataSource;
	    }
	    
	    /**
	     * Get current data source
	     * @return the instance of current data source.
	     */
	    public HttpAccessDataSource get() {
	        return this.dataSource;
	    }
	}
}
