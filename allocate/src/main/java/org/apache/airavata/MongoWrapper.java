package org.apache.airavata;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Created by samkreter on 2/20/16.
 */
public class MongoWrapper {

	private MongoDatabase db;

	public MongoWrapper() {
		db = new MongoClient().getDatabase("resource_allocation");
	}

	/**
	 * Checks whether a user already exists in the database
	 * 
	 * @param user JSON object with the user data to check against
	 * @return boolean value for if the user is there or not
	 */
	public boolean userInDB(JSONObject user) {
		FindIterable<Document> iterator = db.getCollection("user")
				.find(new Document("user.primaryEmail", user.get("primaryEmail")));
		return iterator.iterator().hasNext();
	}

	/**
	 * Creates a new user in the database.
	 * 
	 * @param user JSON object with the user data to store
	 */
	public void createUser(JSONObject user) {
		db.getCollection("user").insertOne(new Document("user",
				new Document().append("name", user.get("name")).append("primaryEmail", user.get("primaryEmail"))));
	}

	/**
	 * Add an entire user json object to the database
	 * 
	 * @param user JSON object with the user data
	 */
	public void addJSONtoDB(JSONObject user) {
		System.out.println(user.toJSONString());

		Document extractUser = new Document("user.primaryEmail", user.get("primaryEmail"));
		Document query2 = new Document();

		query2.append("$set", new Document().append("publications", user.get("publications")).append("institution", new Document("verified", user.get("verified")))
				.append("tier", user.get("tier")).append("funding", user.get("funding")));
		db.getCollection("user").updateMany(extractUser, query2);
	}

	/**
	 * Filter user from database by their email address
	 * 
	 * @param primaryEmail the primary email of the user to query against
	 * @return a JSON object with the user's data
	 */
	public JSONObject getUser(String primaryEmail) {
		FindIterable<Document> iterator = db.getCollection("user")
				.find(new Document("user.primaryEmail", primaryEmail));
		Document d = iterator.iterator().next();
		return (JSONObject) JSONValue.parse(d.toJson());
	}

}
