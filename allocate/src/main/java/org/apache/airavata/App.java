package org.apache.airavata;

import java.util.logging.*;
import java.io.IOException;
import java.util.logging.Level;

import org.json.simple.JSONObject;

/**
 * Hello world!
 *
 */
public class App
{
    private static final Logger log = Logger.getLogger( App.class.getName() );
    public static void main( String[] args ) throws IOException {
    	// TODO get these from the parameters of the payload as a request
        String email = "alberteinstein@princeton.edu";
        String name = "Albert Einstein";
        String fundingNumber = "1052893";
        int tier = 3;
        
    	  JSONObject user = new JSONObject();
        MongoWrapper database = new MongoWrapper();
        Email validator = new Email(email);

        user.put("name", name);
        user.put("primaryEmail", email);
        user.put("tier", tier);
        user.put("verified", validator.validateEmail(email));

        // Check if user is already in database
        boolean userInDatabase = database.userInDB(user);

        // Write user to database if the user was not found at all
        if (!userInDatabase){
            log.log(Level.INFO,  "User not found. Adding user to database");
            database.createUser(user);
            log.log(Level.INFO, "Added user to database");
            // Publications
            String url = "http://scholar.google.com/scholar?hl=en";
            Soup parser = new Soup(url, name);
            user.put("publications", parser.getCitations());

            FundingLookup fl = new FundingLookup(fundingNumber);
            try{
                JSONObject fundingJSON = fl.load();
                user.put("funding", fundingJSON);
            }
            catch( Exception ex ){
                System.err.println(ex);
            }
            
            database.addJSONtoDB(user);
            log.log(Level.INFO, "Wrote user attributes to database");
        } else { // If user is already in database, then skip everything and write new hours requested
            log.log(Level.INFO, "User is already in the database");
            database.addJSONtoDB(user);
            log.log(Level.INFO, "Wrote requested tier and verified email to database");
        }
    }
}
