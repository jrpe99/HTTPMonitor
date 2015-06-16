package dk.jrpe.monitor.db.mongodb;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;

public class MongoDAO {
	public static void main(String[] args) {
		try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {
			MongoDatabase db = mongoClient.getDatabase("httpaccess");
			System.out.println(db.getName());

			MongoCollection<Document> collection = db.getCollection("httpsuccess");
			
	    	Document doc = collection.find(eq("ipAddress", "120.132.100.67")).first();
	    	Double requests = doc.getDouble("requests");
	    	doc.replace("requests", ++requests);
	    	System.out.println(requests);
	    	collection.replaceOne(eq("_id", doc.get("_id")), doc);
	    	//collection.findOneAndReplace(eq("ipAddress", "120.132.100.67"), doc);
		

			/*
			Document doc = new Document("name", "MongoDB")
					.append("type", "database").append("count", 1)
					.append("info", new Document("x", 203).append("y", 102));

			//collection.insertOne(doc);

			//Document myDoc = collection.find().first();
			//System.out.println(myDoc.toJson());

			doc = Document.parse("{'name':'mkyong', 'age':30}");
			
			collection.insertOne(doc);
			
			doc = Document.parse("{'name':'mkyong', 'age':60}");
			
			collection.updateMany(eq("name", "mkyong"), new Document("$set", doc));
			*/
			
		}
	}
}
