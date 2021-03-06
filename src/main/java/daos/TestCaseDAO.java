package daos;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import connectivity.MongoManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.TestCase;
import models.User;
import org.bson.types.ObjectId;

public class TestCaseDAO extends MongoManager {

    public TestCaseDAO() {

    }

    public String insert(TestCase testCase) {
        DBCollection table = db.getCollection("testcase");
        BasicDBObject document = new BasicDBObject();
        
        ObjectId id = new ObjectId();
        
        document.put("_id", id);
        document.put("name", testCase.getName());
        document.put("is_active", testCase.isIsActive());
        document.put("status", testCase.getStatus());
        document.put("lastTested", testCase.getLastTested());
        document.put("timeframe", testCase.getTimeframe().toString());
        document.put("user_id", new ObjectId(testCase.getOwner().getUserId()));
        
        table.insert(document);
        
        return id.toString();
    }
    
    public void update(TestCase testCase) {
        DBCollection table = db.getCollection("testcase");
        BasicDBObject query = new BasicDBObject();
        
	query.put("_id", new ObjectId(testCase.getCase_id()));
        
        BasicDBObject newDocument = new BasicDBObject();
	newDocument.put("name", testCase.getName());
        newDocument.put("is_active", testCase.isIsActive());
        newDocument.put("status", testCase.getStatus());
        newDocument.put("lastTested", testCase.getLastTested());
        newDocument.put("timeframe", testCase.getTimeframe().toString());
        newDocument.put("user_id", new ObjectId(testCase.getOwner().getUserId()));
 
	BasicDBObject updateObj = new BasicDBObject();
	updateObj.put("$set", newDocument);
 
	table.update(query, updateObj);
    }
    
    public List<TestCase> findAll(){
        List<TestCase> testCases = new ArrayList<TestCase>();
        
        DBCollection table = db.getCollection("testcase");
 
	DBCursor cursor = table.find();
	while(cursor.hasNext()) {
            DBObject dbObject = cursor.next();
	    TestCase testCase = new TestCase();
            
            testCase.setCase_id(dbObject.get("_id").toString());
            testCase.setName(dbObject.get("name").toString());
            testCase.setIsActive(((Boolean)dbObject.get("is_active"))?true:false);
            testCase.setStatus(dbObject.get("status").toString());
            testCase.setLastTested((Date)dbObject.get("lastTested"));
            testCase.setTimeframe(TestCase.Timeframes.valueOf(dbObject.get("timeframe").toString()));
            testCase.setOwner(new User(dbObject.get("user_id").toString()));
            testCases.add(testCase);
	}
        return testCases;
    }
    
    public List<TestCase> findById(String id){
        List<TestCase> testCases = new ArrayList<TestCase>();
        
        DBCollection table = db.getCollection("testcase");
 
        BasicDBObject whereQuery = new BasicDBObject();
	whereQuery.put("_id", new ObjectId(id));
        
	DBCursor cursor = table.find(whereQuery);
	while(cursor.hasNext()) {
            DBObject dbObject = cursor.next();
	    TestCase testCase = new TestCase();
            testCase.setCase_id(dbObject.get("_id").toString());
            testCase.setName(dbObject.get("name").toString());
            testCase.setIsActive(((Boolean)dbObject.get("is_active"))?true:false);
            testCase.setStatus(dbObject.get("status").toString());
            testCase.setLastTested((Date)dbObject.get("lastTested"));
            testCase.setTimeframe(TestCase.Timeframes.valueOf(dbObject.get("timeframe").toString()));
            testCase.setOwner(new User(dbObject.get("user_id").toString()));
            testCases.add(testCase);
	}
        return testCases;
    }
    
    public List<TestCase> findByIsActive(){
        List<TestCase> testCases = new ArrayList<TestCase>();
        
        DBCollection table = db.getCollection("testcase");
 
        BasicDBObject whereQuery = new BasicDBObject();
	whereQuery.put("is_active", true);
        
	DBCursor cursor = table.find(whereQuery);
	while(cursor.hasNext()) {
            DBObject dbObject = cursor.next();
	    TestCase testCase = new TestCase();
            testCase.setCase_id(dbObject.get("_id").toString());
            testCase.setName(dbObject.get("name").toString());
            testCase.setIsActive(((Boolean)dbObject.get("is_active"))?true:false);
            testCase.setStatus(dbObject.get("status").toString());
            testCase.setLastTested((Date)dbObject.get("lastTested"));
            testCase.setTimeframe(TestCase.Timeframes.valueOf(dbObject.get("timeframe").toString()));
            testCase.setOwner(new User(dbObject.get("user_id").toString()));
            testCases.add(testCase);
	}
        return testCases;
    }
}