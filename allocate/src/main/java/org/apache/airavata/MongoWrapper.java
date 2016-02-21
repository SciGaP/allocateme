package org.apache.airavata;

import com.mongodb.util.JSON;
import org.bson.Document;
import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import static java.util.Arrays.asList;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Created by samkreter on 2/20/16.
 */
public class MongoWrapper {

	private MongoDatabase db;
	private int results;

	public MongoWrapper() {
		MongoClient mongoClient = new MongoClient();
		db = mongoClient.getDatabase("resource_allocation");
	}

	public boolean userInDB(JSONObject user) {
		FindIterable<Document> iterator = db.getCollection("user")
				.find(new Document("user.primaryEmail", user.get("primaryEmail")));
		return iterator.iterator().hasNext();
	}

	public void createUser(JSONObject user) {
		BasicDBObject documentUser = new BasicDBObject();
		db.getCollection("user").insertOne(new Document("user",
				new Document().append("name", user.get("name")).append("primaryEmail", user.get("primaryEmail"))));
	}

	/**
     * Take a string formatted representation of the data and add it to the database
     */
	
    public void addJSONtoDB(JSONObject user){
    	System.out.println(user.toJSONString());
    	System.out.println("adding");
    	
    	Document extractUser = new Document("user.primaryEmail", user.get("primaryEmail"));
    	Document query2 = new Document();
    	
//    	query2.append("$set", new Document()
//				.append("tier", user.get("tier")));
    	
//    	.append("publications", (JSONArray) user.get("publications"))
    	query2.append("$set", new Document()
											.append("institution", new Document("verified", user.get("verified")))
											.append("tier",user.get("tier"))
											.append("funding", user.get("funding")));
    	db.getCollection("user").updateMany(extractUser, query2);
    	System.out.println("adding done");
       }

	/**
	 * @param primaryEmail
	 *            The user's primary email address to query the database.
	 *            Returns a JSONObject containing all of the user's data
	 */
	public JSONObject getUser(String primaryEmail) {
		FindIterable<Document> iterator = db.getCollection("user")
				.find(new Document("user.primaryEmail", primaryEmail));
		Document d = iterator.iterator().next();
		return (JSONObject) JSONValue.parse(d.toJson());
	}

}
