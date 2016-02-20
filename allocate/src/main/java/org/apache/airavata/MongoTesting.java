package org.apache.airavata;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import org.json.simple.JSONObject;


/**
 * Created by samkreter on 2/20/16.
 */
public class MongoTesting {

    private MongoDatabase db;

    public MongoTesting(){
        MongoClient mongoClient = new MongoClient();

        db = mongoClient.getDatabase("test");
    }

    public void printData(){
        FindIterable<Document> iterable = db.getCollection("restaurants").find();
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                JSONObject o =
                System.out.println(document.get("address"));
            }
        });
    }



}
