package org.apache.airavata;

import java.util.logging.*;
import java.io.IOException;
import java.util.logging.Level;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/**
 * Hello world!
 *
 */
public class App
{
    private static final Logger log = Logger.getLogger( App.class.getName() );
    public static void main( String[] args ) throws IOException {
        JSONObject publications, funding, institution, verified, hours;

        MongoTesting database = new MongoTesting();

        JSONObject user = new JSONObject();
        user.put("name", "Sameet Sapra");
        user.put("primaryEmail", "sameet.sapra@gmail.com");
        user.put("nsfFundingNum", "1052893");
        
        String tempID = "1052893"; // CHANGE THIS TO REAL INPUT
        FundingLookup fl = new FundingLookup(tempID);
        try{
            JSONObject fundingJSON = fl.load();
        } catch(Exception ex){
            System.err.println(ex);
        }
        
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
//        String url = "http://scholar.google.com/scholar?hl=en";
//        String professorName = "Matthew Caesar";
//        Soup parser = new Soup(url, professorName);
//        publications = new JSONObject();
//        publications.put("publications", parser.getCitations());

        } else {
            log.log(Level.INFO, "User is already in the database");

            
        }

    }
}
