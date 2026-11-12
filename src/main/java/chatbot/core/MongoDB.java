package chatbot.core;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import io.github.cdimascio.dotenv.Dotenv;

public class MongoDB {
    static Dotenv dotenv = Dotenv.load();
    static String localToken = dotenv.get("MONGODB_LOCAL");
    static String mongoURI = dotenv.get("MONGODB_URI");

    //Локально
    private static final MongoClient localClient = MongoClients.create(localToken);
    private static final MongoDatabase localDB = localClient.getDatabase("chatbot");

    public static MongoCollection<Document> logs(){
        return localDB.getCollection("logs");
    }

    public static MongoCollection<Document> cities(){
        return localDB.getCollection("cities");
    }

    //В облаке
    private static final MongoClient cloudClient = MongoClients.create(mongoURI);
    private static final MongoDatabase cloudDb = cloudClient.getDatabase("chatbot");

    public static MongoCollection<Document> cloudLogs() {
        return cloudDb.getCollection("logs");
    }

    public static void saveLog(Document logDoc) {
        try {
            logs().insertOne(logDoc);
        } catch (Exception e) {
            System.err.println("Ошибка записи в локальную БД: " + e.getMessage());
        }

        try {
            cloudLogs().insertOne(logDoc);
        } catch (Exception e) {
            System.err.println("Ошибка записи в облако Atlas: " + e.getMessage());
        }
    }
}
