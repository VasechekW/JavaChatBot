package main.java.chatbot.core;

import main.java.chatbot.core.command.BotCommand;

public class ChatBot {

    private final DataBaseBot dataBaseBot;
    private final BotContext context;

    public ChatBot(DataBaseBot dataBaseBot){

        this.dataBaseBot = dataBaseBot;
        this.context = new BotContext();
    }

    public String getGreeting(){
        return "=== Чат-бот запущен ===\n" +
                "Напишите 'помощь' для списка команд или 'выход' для завершения.\n";
    }

    public String questionInput(String input){
        String question = input.trim().toLowerCase();

        if (question.isEmpty()) {
            return "Вы ничего не ввели, попробуйте ещё раз.";
        }

        BotCommand command = dataBaseBot.findCommand(question);
        if (command == null) {
            return "Я не знаю ответа на этот вопрос.";
        }

        return command.execute(context, "");
    }
}