package dk.jrpe.monitor.db.datasource;

import dk.jrpe.monitor.db.datasource.cassandra.CassandraDataSource;
import dk.jrpe.monitor.db.datasource.inmemory.InMemoryDataSource;

public final class DataSourceFactory {
	private HttpAccessDataSource dataSource;
	
    private static class InstanceHolder {
        static final DataSourceFactory instance = new DataSourceFactory();
    }

	private DataSourceFactory() {
		/*
		 * Init possibly from property file
		 */
		this.dataSource = Type.IN_MEMORY.get();
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
	 * @param source
	 */
	public static void set(DataSourceFactory.Type source) {
		InstanceHolder.instance.dataSource = source.get();
	}
	
	public enum Type {
	    CASSANDRA(new CassandraDataSource()), IN_MEMORY(new InMemoryDataSource());
	    
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
