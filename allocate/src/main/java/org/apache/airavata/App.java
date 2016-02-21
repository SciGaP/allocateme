package org.apache.airavata;

import java.util.logging.*;
import java.io.IOException;
import java.util.logging.Level;

import org.apache.airavata.db.DBWrapper;
import org.json.simple.JSONObject;

/**
 * Hello world!
 *
 */
public class App
{
    private static final Logger log = Logger.getLogger( App.class.getName() );
    public static void main( String[] args ) throws IOException {

    	JSONObject user = new JSONObject();
    	
        DBWrapper database = new DBWrapper();
        String email = "sameet.sapra@gmail.com";
        String name = "Matthew Caesar";
        int tier = 1;
        
        user.put("name", name);
        user.put("primaryEmail", email);
        user.put("tier", tier);
        Email validator = new Email(email);
        user.put("verified", validator.validateEmail(email));
        
        database.addJSONtoDB(user);

        // Get user's name / email

        // Check if user is already in database
        boolean userInDatabase = database.userInDB(user);

        // If user is already in database, then skip everything and write new hours requested

        // Otherwise write user to database
        if (!userInDatabase){
            log.log(Level.INFO,  "User not found. Adding user to database");
            database.createUser(user);
            log.log(Level.INFO, "Added user to database");
            // Publications
//	        String url = "https://scholar.google.com/scholar?hl=en";
//	        Soup parser = new Soup(url, name);
//	        user.put("publications", parser.getCitations());

	        String fundingNumber = "1052893";
            FundingLookup fl = new FundingLookup(fundingNumber);
            try{
                JSONObject fundingJSON = fl.load();
                user.put("funding", fundingJSON);
            }
            catch( Exception ex ){
                System.err.println(ex);
            }
            database.addJSONtoDB(user);

        } else {
            log.log(Level.INFO, "User is already in the database");
            database.addJSONtoDB(user);
        }
    }
}
