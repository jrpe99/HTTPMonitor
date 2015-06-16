package dk.jrpe.monitor.db.datasource.mongodb;

import dk.jrpe.monitor.db.inmemory.httpaccess.InMemoryDataBase;
import dk.jrpe.monitor.db.mongodb.dao.httpaccess.MongoDBDAO;
import dk.jrpe.monitor.db.datasource.HttpAccessDataSource;
import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;

import java.util.List;

/**
 * Data source for the a MongoDB database.
 * @author Jörgen Persson
 */
public class MongoDBDataSource implements HttpAccessDataSource {

    private final InMemoryDataBase dataBase = InMemoryDataBase.getInstance();
    private final MongoDBDAO mongoDb = MongoDBDAO.getInstance();

    @Override public List<HTTPAccessTO> getHttpSuccess() {
        return this.mongoDb.getHttpSuccess();
    }

    @Override public List<HTTPAccessTO> getHttpFailed() {
        return this.mongoDb.getHttpFailed();
    }

    @Override public List<HTTPAccessTO> getHttpSuccessPerMinute(String date, String from, String to) {
        return this.dataBase.getHttpSuccessPerMinute(date, from, to);
    }
    
    @Override public List<HTTPAccessTO> getHttpFailedPerMinute(String date, String from, String to) {
        return this.dataBase.getHttpFailedPerMinute(date, from, to);
    }

    @Override public void saveHttpAccess(HTTPAccessTO to, int hour) {
        // Not used
    }

    @Override public void updateHttpSuccess(HTTPAccessTO to) {
        this.mongoDb.updateHttpSuccess(to);
    }

    @Override public void updateHttpSuccessPerMinute(HTTPAccessTO to) {
        this.dataBase.updateHttpSuccessPerMinute(to);
    }

    @Override public void updateHttpFailed(HTTPAccessTO to) {
        this.dataBase.updateHttpFailed(to);
    }

    @Override public void updateHttpFailedPerMinute(HTTPAccessTO to) {
        this.dataBase.updateHttpFailedPerMinute(to);
    }

    @Override public void open() {
        // NOT USED
    }

    @Override public void close() throws Exception {
        // NOT USED
    }

	@Override public void clear() {
		this.dataBase.clear();
	}
}
