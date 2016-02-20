package org.apache.airavata;



import java.io.IOException;







/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws IOException {

        MongoTesting dbTest = new MongoTesting();

        dbTest.printData();


//        String url = "http://news.ycombinator.com";
//        Soup parser = new Soup(url);
//        parser.getCitations();
    }
}
