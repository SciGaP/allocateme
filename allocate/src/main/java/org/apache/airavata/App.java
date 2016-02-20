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


        String url = "http://scholar.google.com/scholar?hl=en";
        String professorName = "Matthew Caesar";
        Soup parser = new Soup(url, professorName);
        parser.getCitations();

    }
}
