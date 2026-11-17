package chatbot.core;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Date;

public class MongoDB {
    private final MongoDatabase db;

    public MongoDB(String uri){
        MongoClient client = MongoClients.create(uri);
        this.db = client.getDatabase("chatbot");
    }

    public MongoCollection<Document> cities(){
        return db.getCollection("cities");
    }

    public MongoCollection<Document> logs(){
        return db.getCollection("logs");
    }

    public void saveProcessMessage(long userId, String username, String firstname, String input, String res, String state){
        Document logDoc = new Document("userId", userId).append("username", username).append("firstName", firstname)
                .append("input", input).append("response", res).append("state", state).append("time", new Date());

        try {
            logs().insertOne(logDoc);
        } catch (Exception e) {
            System.err.println("Ошибка записи логов: " + e.getMessage());
        }
    }

    public void saveProcessGreeting(long userId, String username, String firstname, String greet){
        Document logDoc = new Document("userId", userId).append("username", username).append("firstName", firstname)
                .append("input", "/start").append("response", greet).append("state", "IDLE").append("time", new Date());

        try {
            logs().insertOne(logDoc);
        } catch (Exception e) {
            System.err.println("Ошибка записи логов: " + e.getMessage());
        }
    }
}
