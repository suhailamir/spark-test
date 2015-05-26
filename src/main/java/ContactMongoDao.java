/**
 * Created by suhail on 5/25/15.
 */
import com.mongodb.*;

import java.sql.Date;
import java.util.ArrayList;

public class ContactMongoDao<T extends Contact> implements ContactDbService<T> {
    private DBCollection collection;

    public ContactMongoDao() {

        try {
            MongoClient mongoClient = new MongoClient("localhost");
            DB db                   = mongoClient.getDB("sparkledb");
            collection              = db.getCollection("Contacts");

            System.out.println("Connecting to MongoDB@" + mongoClient.getAllAddress());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public Boolean create(T entity) {
        BasicDBObject doc = new BasicDBObject("name", entity.getName()).
                append("id", entity.getId()).
                append("name", entity.getName()).
                append("email", entity.getEmail()).
                append("deleted", false).
                append("createdAt", new Date(new java.util.Date().getTime()));

        collection.insert(doc);
        return true;
    }


    @SuppressWarnings("unchecked")
    public T readOne(int id) {
        BasicDBObject query = new BasicDBObject("id", id);
        DBCursor cursor = collection.find(query);

        try {
            if(cursor.hasNext()) {
                BasicDBObject doc = (BasicDBObject) cursor.next();
                Contact entity = new Contact(
                        doc.getInt("id"),
                        doc.getString("name"),
                        doc.getString("email"),
                        doc.getDate("createdAt"),
                        doc.getBoolean("deleted")
                );

                return (T) entity;
            } else {
                return null;
            }
        } finally {
            cursor.close();
        }
    }


    @SuppressWarnings("unchecked")
    public ArrayList<T> readAll() {
        // When you use DBCollection::find() without an argument it defaults to find all
        DBCursor cursor = collection.find();

        ArrayList<Contact> results = (ArrayList<Contact>) new ArrayList<T>();

        try {
            while(cursor.hasNext()) {
                BasicDBObject doc = (BasicDBObject) cursor.next();

                Contact entity = new Contact(
                        doc.getInt("id"),
                        doc.getString("name"),
                        doc.getString("email"),
                        doc.getDate("createdAt"),
                        doc.getBoolean("deleted")
                );

                results.add(entity);
            }

            return (ArrayList<T>) results;
        } finally {
            cursor.close();
        }
    }


    public Boolean update(int id, String name, String email) {
        BasicDBObject query = new BasicDBObject("id", id);
        DBCursor cursor = collection.find(query);
        try {
            if(cursor.hasNext()) {
                BasicDBObject doc = (BasicDBObject) cursor.next();
                doc.put("name", name);
                doc.put("email", email);
                collection.save(doc);
                return true;
            } else {
                return false;
            }
        } finally {
            cursor.close();
        }
    }


    public Boolean delete(int id) {
        BasicDBObject query = new BasicDBObject("id", id);
        DBCursor cursor = collection.find(query);
        try {
            if(cursor.hasNext()) {
                collection.remove(cursor.next());
              return true;
            } else {
                return false;
            }
        } finally {
            cursor.close();
        }
    }
}