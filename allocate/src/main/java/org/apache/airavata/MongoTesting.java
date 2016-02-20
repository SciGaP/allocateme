package org.apache.airavata;

import org.bson.Document;
import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import static java.util.Arrays.asList;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by samkreter on 2/20/16.
 */
public class MongoTesting {

    private MongoDatabase db;
    private int results;
    public MongoTesting(){
        MongoClient mongoClient = new MongoClient();
        db = mongoClient.getDatabase("resource_allocation");
    }

    public boolean userInDB(JSONObject user){
        FindIterable<Document> iterator = db.getCollection("user").find(
                new Document("user.primaryEmail", user.get("primaryEmail"))
        );
        return iterator.iterator().hasNext();
    }

    public void createUser(JSONObject user) {
        BasicDBObject documentUser = new BasicDBObject();
        db.getCollection("user").insertOne(
                new Document("user",
                        new Document()
                                .append("name", user.get("name"))
                                .append("primaryEmail", user.get("primaryEmail")))
        );
    }

    public void update(JSONObject user){

    }

}