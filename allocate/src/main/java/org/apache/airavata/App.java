package org.apache.airavata;
import java.io.IOException;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;
/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws IOException {
        String url = "http://scholar.google.com/scholar?hl=en";
        String professorName = "Matthew Caesar";
        Soup parser = new Soup(url, professorName);
        parser.getCitations();
    }
}
