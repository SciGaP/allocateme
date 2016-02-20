package org.apache.airavata;



import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws IOException {
        user.put("nsfFundingNum", "1052893");
        
        String tempID = "1052893"; // CHANGE THIS TO REAL INPUT
        FundingLookup fl = new FundingLookup(tempID);
        try{
            JSONObject fundingJSON = fl.load();
        } catch(Exception ex){
            System.err.println(ex);
        }
        


        MongoTesting dbTest = new MongoTesting();

        dbTest.printData();


        String url = "http://scholar.google.com/scholar?hl=en";
        String professorName = "Matthew Caesar";
        Soup parser = new Soup(url, professorName);
        parser.getCitations();

    }
}
