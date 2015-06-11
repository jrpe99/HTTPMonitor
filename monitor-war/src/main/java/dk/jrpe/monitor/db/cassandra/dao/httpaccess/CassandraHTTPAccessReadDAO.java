package dk.jrpe.monitor.db.cassandra.dao.httpaccess;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import dk.jrpe.monitor.db.cassandra.CassandraConnectionHandler;
import java.util.List;

/**
 * Cassandra read DAO for HTTP access data.
 * @author Jörgen Persson
 */
public class CassandraHTTPAccessReadDAO extends CassandraDAO {

    public CassandraHTTPAccessReadDAO(CassandraConnectionHandler conn) {
        super(conn);
    }
    
    /**
     * See {@link dk.jrpe.monitor.db.datasource.HttpAccessDataSource#getHttpSuccess()}
     */
    public List<Row> getHttpSuccess() {
        String cql = "SELECT ip_address, requests from httpaccess.http_success";
        ResultSet resultList = conn.execute(cql);
        return resultList.all();
    }

    /**
     * See {@link dk.jrpe.monitor.db.datasource.HttpAccessDataSource#getHttpFailed()}
     */
    public List<Row> getHttpFailed() {
        String cql = "SELECT ip_address, requests from httpaccess.http_failed";
        ResultSet resultList = conn.execute(cql);
        return resultList.all();
    }

    /**
     * See {@link dk.jrpe.monitor.db.datasource.HttpAccessDataSource#getHttpSuccessPerMinute(String, String, String)}
     */
    public List<Row> getHttpSuccessPerMinute(String date, String from, String to) {
        StringBuilder cql = new StringBuilder();
        cql.append(" SELECT * from httpaccess.http_success_per_minute");
        cql.append(" where");
        cql.append(" date='").append(date).append("'");
        cql.append(" and");
        cql.append(" date_to_minute >= '").append(from).append("'");
        cql.append(" and");
        cql.append(" date_to_minute <= '").append(to).append("'");
        ResultSet resultList = conn.execute(cql.toString());
        return resultList.all();
    }

    /**
     * See {@link dk.jrpe.monitor.db.datasource.HttpAccessDataSource#getHttpFailedPerMinute(String, String, String)}
     */
    public List<Row> getHttpFailedPerMinute(String date, String from, String to) {
        StringBuilder cql = new StringBuilder();
        cql.append(" SELECT * from httpaccess.http_failed_per_minute");
        cql.append(" where");
        cql.append(" date='").append(date).append("'");
        cql.append(" and");
        cql.append(" date_to_minute >= '").append(from).append("'");
        cql.append(" and");
        cql.append(" date_to_minute <= '").append(to).append("'");
        ResultSet resultList = conn.execute(cql.toString());
        return resultList.all();
    }
}
