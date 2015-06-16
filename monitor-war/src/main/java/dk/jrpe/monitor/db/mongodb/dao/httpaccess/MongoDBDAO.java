package dk.jrpe.monitor.db.mongodb.dao.httpaccess;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import dk.jrpe.monitor.db.httpaccess.to.JsonHTTPAccessTO;
import dk.jrpe.monitor.json.JSONMapper;
import dk.jrpe.monitor.source.httpaccess.to.HTTPAccessTOFactory;
import static com.mongodb.client.model.Filters.*;


/**
 * Singelton<br>
 * Implementation of an in-memory database for HTTP access data with 
 * the same "schema" as the Cassandra database schema.<br>
 * See {@link dk.jrpe.monitor.db.cassandra.schema.HTTPAccessSchema}
 * @author Jörgen Persson
 */
public class MongoDBDAO {
    private static class InstanceHolder {
        static final MongoDBDAO instance = new MongoDBDAO();
    }

    /*
      <IP Address, RequestCount>
    */
    private final ConcurrentHashMap<String,Long> httpSuccess = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String,Long> httpFailed = new ConcurrentHashMap<>();

    /*
      HTTP Access success <Date to minute, RequestCount>
    */
    private final ConcurrentSkipListMap<String,Long> httpSuccessPerMinute = new ConcurrentSkipListMap<>();
    /*
      HTTP Access failed <Date to minute, RequestCount>
    */
    private final ConcurrentSkipListMap<String,Long> httpFailedPerMinute = new ConcurrentSkipListMap<>();

    private MongoDatabase db;

    /**
     * Return the instance of the in memory database.
     * @return the instance for the in memory database
     */
    public static MongoDBDAO getInstance() {
        return InstanceHolder.instance;
    }

    
	public MongoDBDAO() {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		this.db = mongoClient.getDatabase("httpaccess");
	}


	/**
     * Clear the database - remove ALL data
     */
    public void clear() {
    	this.httpSuccess.clear();
    	this.httpFailed.clear();
    	this.httpSuccessPerMinute.clear();
    	this.httpFailedPerMinute.clear();
    }
    
    public List<HTTPAccessTO> getHttpSuccess() {
    	List<HTTPAccessTO> resultList = new ArrayList<>();
    	MongoCollection<Document> collection = db.getCollection("httpsuccess");
    	for(Document doc: collection.find()) {
    		doc.remove("_id");
    		JsonHTTPAccessTO to = JSONMapper.toJsonHTTPAccessTO(doc.toJson());
    		resultList.add(HTTPAccessTOFactory.convertToDbTo(to));
    	}
    	
    	return resultList;
    }

    public List<HTTPAccessTO> getHttpFailed() {
    	List<HTTPAccessTO> resultList = new ArrayList<>();
    	MongoCollection<Document> collection = db.getCollection("httpfailed");
    	for(Document doc: collection.find()) {
    		doc.remove("_id");
    		JsonHTTPAccessTO to = JSONMapper.toJsonHTTPAccessTO(doc.toJson());
    		resultList.add(HTTPAccessTOFactory.convertToDbTo(to));
    	}
    	return resultList;
    }

    public List<HTTPAccessTO> getHttpSuccessPerMinute(String date, String from, String to) {
        return getHttpAccessPerMinute(this.httpSuccessPerMinute.tailMap(from));
    }
    
    public List<HTTPAccessTO> getHttpFailedPerMinute(String date, String from, String to) {
        return getHttpAccessPerMinute(this.httpFailedPerMinute.tailMap(from));
    }

    public void updateHttpSuccess(HTTPAccessTO to) {
    	MongoCollection<Document> collection = db.getCollection("httpsuccess");
    	Document doc = collection.find(eq("ipAddress", to.getIpAddress())).first();
    	if(doc == null) {
    		collection.insertOne(Document.parse(JSONMapper.toJSON(to)));
    	} else {
        	Integer requests = doc.getInteger("requests");
        	doc.replace("requests", ++requests);
	    	collection.replaceOne(eq("_id", doc.get("_id")), doc);
    	}
	}

    public void updateHttpSuccessPerMinute(HTTPAccessTO to) {
        Long requests = this.httpSuccessPerMinute.putIfAbsent(to.getDateToMinute(), new Long("1"));
        if(requests != null) {
            this.httpSuccessPerMinute.put(to.getDateToMinute(), ++requests);
        }
    }

    public void updateHttpFailed(HTTPAccessTO to) {
        Long requests = this.httpFailed.putIfAbsent(to.getIpAddress(), new Long("1"));
        if(requests != null) {
            this.httpFailed.put(to.getIpAddress(), ++requests);
        }
    }

    public void updateHttpFailedPerMinute(HTTPAccessTO to) {
        Long requests = this.httpFailedPerMinute.putIfAbsent(to.getDateToMinute(), new Long("1"));
        if(requests != null) {
            this.httpFailedPerMinute.put(to.getDateToMinute(), ++requests);
        }
    }

    private List<HTTPAccessTO> getHttpAccessPerMinute(ConcurrentNavigableMap<String, Long> periodMap) {
        return periodMap.values().stream()
            .map((value) -> new HTTPAccessTO.Builder().setRequests(value).build())
            .collect(Collectors.toList());
    }    
}
