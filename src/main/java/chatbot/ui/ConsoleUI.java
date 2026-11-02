package chatbot.ui;

import chatbot.core.ChatBot;
import chatbot.core.DataBaseBot;

import java.util.Scanner;

public class ConsoleUI {
    public static void main(String[] args){

        DataBaseBot dataBaseBot = new DataBaseBot();
        ChatBot bot = new ChatBot(dataBaseBot);
        Scanner scanner = new Scanner(System.in);

        System.out.print(bot.getGreeting());

        while (true){
            System.out.print("ВЫ: ");

            if (!scanner.hasNextLine()) break;

            String userInput = scanner.nextLine();

            String botResponse = bot.questionInput(userInput);
            System.out.println("Бот: " + botResponse);
        }
    }
}