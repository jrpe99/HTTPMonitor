package dk.jrpe.monitor.db.httpaccess.to;

public class MongoDBHTTPAccessTO extends JsonHTTPAccessTO {
	private ObjectId _id;

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	
	public class ObjectId {
		private String id;
		ObjectId(String id) {
			this.id = id;
		}
	}
	
}
