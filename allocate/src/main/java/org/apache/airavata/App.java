package org.apache.airavata;

import org.apache.airavata.db.DBWrapper;
import org.apache.airavata.model.user.Publication;
import org.apache.airavata.model.user.UserProfile;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
    	UserProfile user = new UserProfile();
        DBWrapper database = new DBWrapper();
        Email validator = new Email(email);

        user.setName(name);
        user.setEmail(email);
        user.setTier(tier);

        // Check if user is already in database
        boolean userInDatabase = database.isExists(email);

        // Write user to database if the user was not found at all
        if (!userInDatabase){
            log.log(Level.INFO,  "User not found. Adding user to database");
            database.createUserProfile(user);
            log.log(Level.INFO, "Added user to database");
            // Publications
            String url = "http://scholar.google.com/scholar?hl=en";
            Soup parser = new Soup(url, name);
            ArrayList<Publication> publications = new ArrayList<>();
            for(int i=0;i<parser.getCitations().size();i++){
                Publication publication = new Publication();
                publication.setName(((JSONObject)parser.getCitations().get(i)).get("name").toString());
                publication.setNumCitations(((JSONObject) parser.getCitations().get(i)).get("num_citations").toString());
            }
            user.setPublications(publications);

            FundingLookup fl = new FundingLookup(fundingNumber);
            try{
                JSONObject fundingJSON = fl.load();
                user.addToFunding(fundingJSON.get("award").toString());
            }
            catch( Exception ex ){
                System.err.println(ex);
            }
            
            database.updateUserProfile(user);
            log.log(Level.INFO, "Wrote user attributes to database");
        } else { // If user is already in database, then skip everything and write new hours requested
            log.log(Level.INFO, "User is already in the database");
            database.createUserProfile(user);
            log.log(Level.INFO, "Wrote requested tier and verified email to database");
        }
    }
}
