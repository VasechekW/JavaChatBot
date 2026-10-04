package main.java.chatbot.core;

public class ChatBot {
    private final DataBaseBot dataBaseBot;

    public ChatBot(DataBaseBot dataBaseBot){
        this.dataBaseBot = dataBaseBot;
    }

    public String getGreeting(){
        return "=== Чат-бот запущен ===\n" +
                "Напишите 'помощь' для списка команд или 'выход' для завершения.\n";
    }

    public String questionInput(String input){
        String inputTrim = input.trim();

        return dataBaseBot.getAnswer(inputTrim);
    }
}