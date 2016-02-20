/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.airavata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author Peter Dirks
 */
public class FundingLookup implements Runnable {
    String fundingID;
    Thread runner;
    
    private final String baseURLString = "http://api.nsf.gov/services/v1/awards/";
    private final String fileType = ".json?callback=processJson";    // can also be .xml
    
    private URL url;
    private JSONObject jsonObj;
    private String urlString = "";
    
    // JSON-related data
    private String agency       = "";
    private String awardeeName  = "";
    private String fundsObligatedAmt = "";
    private String id           = "";
    private String piFirstName  = "";
    private String piLastName   = "";
    private String date         = "";        // TODO make into a date object
    private String title        = "";
    
    /**
     * Default Constructor
     * @param fundingID grant ID to look up, currently only searches NSF db
     */
    public FundingLookup( String fundingID ){
        this.fundingID = fundingID;
        jsonObj = null;
        url = null;
    }
    
    /**
     * Load up data from NSF API. Some minimal error-checking done.
     * @throws Exception UnsupportedEncodingException on bad URL encode, 
     * @throws MalformedURLException on bad URL object creation
     * @throws IOException on IO create failure
     */
    public void load () throws Exception
    {
        if( fundingID == null || fundingID.equals("") ){
            throw new Exception("Empty funding ID.");
        }
        
        String encodedFundingID;    // create an encoded urlString
        try{
            encodedFundingID = URLEncoder.encode( fundingID, "UTF-8" );
        } catch(UnsupportedEncodingException ex){
            throw ex;
        }
        
        // create out URL object
        urlString = baseURLString + encodedFundingID + fileType;
        try{
            url = new URL(urlString);
        } catch( MalformedURLException muex ){
            throw muex;
        }
        
        String jsonString = "";
        try{
            BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream())
            );
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                jsonString += inputLine;
            }
            in.close();
        } catch( IOException ioex ){
            System.err.println("ioex: "+ioex);
            throw ioex;
        }
        
        
        try {
            parseFunding(jsonString);
        } catch (Exception ex) {
            System.err.println("parse: "+ex);
            throw ex;
        }
        
    }// end load
    
    /**
     * Parse raw JSON string into JSON object and strings.
     * @param jsonString raw JSON from input reader
     * @throws Exception 
     */
    private void parseFunding( String jsonString ) throws Exception{
        if (jsonString == null || jsonString == "") return;
        
        // populate JSON object
        try {
            jsonObj = (JSONObject)JSONValue.parse(jsonString);
        } catch (Exception ex) {
            throw ex;
        }
        
        // error checks
        if (jsonObj == null) return;
        String status = "";
        try {
            status = (String)jsonObj.get("status");
        } catch (Exception ex) {
            throw ex;
        }
        if (status == null) {
            throw new Exception("Status returned from API was null.");
        }
        else if(!status.equals("OK")) {
            throw new Exception("Status returned from API was not OK.");
        }
        
        try {
            jsonObj = (JSONObject)jsonObj.get("response");
        } catch (Exception ex) {
            throw ex;
        }
        
        // separate JSON children into individual JSON objects, place
        //  json objects into a array-like data structure
        JSONArray docs; 
        try {
            docs = (JSONArray)jsonObj.get("docs");
        } catch (Exception ex) {
            throw ex;
        }
        
        for( Object doc : docs ){
            try{
                JSONObject fundingInfo = (JSONObject) doc;
                
                agency = (String)fundingInfo.getOrDefault( "agency", "");
                awardeeName = (String)fundingInfo.getOrDefault( "awardeeName", "");
                fundsObligatedAmt = (String)fundingInfo.getOrDefault( "fundsObligatedAmt", "");
                id = (String)fundingInfo.getOrDefault( "id", "");
                piFirstName = (String)fundingInfo.getOrDefault( "piFirstName", "");
                piLastName = (String)fundingInfo.getOrDefault( "piLastName", "");
                date = (String)fundingInfo.getOrDefault( "date", "");        // TODO make into a date object
                title = (String)fundingInfo.getOrDefault( "title", "");
                
            } catch (Exception ex){
                throw ex;
            }
            
        }
        
    }//end parseFunding
    
    /**
     * Performs the load() and parseFunding() as a separate thread.
     * @param threadName some arbitrary thread name
     */
    public void loadParallel( String threadName ){
        runner = new Thread( this, threadName );
        runner.start();
        return;
    }
    public void run(){
        try{
            load();
        }
        catch( Exception ex ){}
        return;
    }
    
    /**
     * getJSONObj
     * @return JSONObject represents everything found in received API call
     */
    JSONObject getJSONObj(){
        return this.jsonObj;
    }
    
    /**
     * getAgency
     * @return String representation of agency awarding funding
     */
    String getAgency(){
        return this.agency;
    }
    
    /**
     * get AwardeeName
     * @return String representation of awarding institution
     */
    String getAwardeeName(){
        return this.awardeeName;
    }
    
    /**
     * getFundsObligatedAmt
     * @return String representation of awarded funds
     */
    String getFundsObligatedAmt(){
        return this.fundsObligatedAmt;
    }
    
    /**
     * getId
     * @return String representation of funding ID
     */
    String getId(){
        return this.id;
    }
    
    /**
     * getPiFirstName
     * @return String representation of owner's first name
     */
    String getPiFirstName(){
        return this.piFirstName;
    }
    
    /**
     * getPiLastName
     * @return String representation of owner's last name
     */
    String getPiLastName(){
        return this.piLastName;
    }
    
    /**
     * getDate
     * @return String representation of grant's award date 
     */
    String getDate(){
        return this.date;
    }
    
    /**
     * getTitle
     * @return String representation of grant's title
     */
    String getTitle(){
        return this.title;
    }
    
}// end FundingLookup Class