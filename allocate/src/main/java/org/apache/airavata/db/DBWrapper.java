/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/
package org.apache.airavata.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.apache.airavata.model.user.UserProfile;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DBWrapper {
    private final static Logger logger = LoggerFactory.getLogger(DBWrapper.class);

    private MongoDatabase db;
    private ModelConversionHelper modelConversionHelper;

    public DBWrapper(){
        MongoClient mongoClient = new MongoClient();
        db = mongoClient.getDatabase("resource_allocation");
        modelConversionHelper = new ModelConversionHelper();
    }

    /**
     * Check whether there is an existing UserProfile
     * @param email
     * @return
     */
    public boolean isExists(String email) {
        FindIterable<Document> iterator = db.getCollection("user")
                .find(new Document("user.email", email));
        return iterator.iterator().hasNext();
    }


    /**
     * Creates new UserProfile
     * @param userProfile
     * @throws JsonProcessingException
     */
    public void createUserProfile(UserProfile userProfile) throws JsonProcessingException {
        db.getCollection("user").insertOne(Document.parse(modelConversionHelper.serializeObject(userProfile)));
    }

    /**
     * Updates existing UserProfile
     * @param userProfile
     * @throws JsonProcessingException
     */
    public void updateUserProfile(UserProfile userProfile) throws JsonProcessingException {
        db.getCollection("user").updateOne(new BasicDBObject("email", userProfile.getEmail()),new Document("$set",
                Document.parse(modelConversionHelper.serializeObject(userProfile))));
    }

    /**
     * Retrieve existing UserProfile providing email
     * @param email
     * @return
     * @throws IOException
     */
    public UserProfile getUserProfile(String  email) throws IOException {
        FindIterable<Document> iterator = db.getCollection("user").find(new Document("email", email)).limit(1);
        if(iterator.iterator().hasNext()){
            return (UserProfile)modelConversionHelper.deserializeObject(UserProfile.class, iterator.iterator().next().toJson());
        }else{
            return null;
        }
    }

    /**
     * Remove existing UserProfile providing user email
     * @param email
     */
    public void removeUserProfile(String email) {
        FindIterable<Document> iterator = db.getCollection("user").find(new Document("email", email)).limit(1);
        if(iterator.iterator().hasNext()){
            db.getCollection("user").deleteOne(iterator.iterator().next());
        }
    }
}