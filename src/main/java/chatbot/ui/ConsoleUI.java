package chatbot.ui;

import chatbot.core.ChatBot;
import chatbot.core.DataBaseBot;
import chatbot.core.MongoDB;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Scanner;

public class ConsoleUI {
    public static void main(String[] args){
        Dotenv dotenv = Dotenv.load();

        String mode = args[0];


        String uri = "null";
        if (mode.equals("local")){
            uri = dotenv.get("MONGODB_LOCAL_PATH");
        }
        else if (mode.equals("cloud")){
            uri = dotenv.get("MONGODB_URI");
        }

        if (uri == null || uri.isEmpty()){
            System.err.println("Не введен URI");
            return;
        }

        MongoDB db = new MongoDB(uri);

        DataBaseBot dataBaseBot = new DataBaseBot(db);
        ChatBot bot = new ChatBot(dataBaseBot, db);
        Scanner scanner = new Scanner(System.in);

        System.out.print(bot.processGreeting());

        while (true){
            System.out.print("ВЫ: ");

            if (!scanner.hasNextLine()) break;

            String userInput = scanner.nextLine();

            String botResponse = bot.processMessage(userInput);
            System.out.println("Бот: " + botResponse);
        }
    }
}