package chatbot.core;

import chatbot.core.command.BotCommand;
import org.bson.Document;
import java.util.Date;

public class ChatBot {

    private final DataBaseBot dataBaseBot;
    private final BotContext context;

    public ChatBot(DataBaseBot dataBaseBot){
        this.dataBaseBot = dataBaseBot;
        this.context = new BotContext();
    }

    public String processGreeting() {
        return processGreeting(1L, null, null); // Передаем тестовый ID
    }

    public String processMessage(String input) {
        return processMessage(1L, null, null, input); // Передаем тестовый ID
    }

    public String processMessage(long userId, String username, String firstname, String input){
        String state = context.getState(userId).toString();
        String res = questionInput(userId, input);

        Document logDoc = new Document("userId", userId).append("username", username).append("firstName", firstname)
                .append("input", input).append("response", res).append("stateBefore", state).append("time", new Date());
        MongoDB.saveLog(logDoc);

        return res;
    }

    public String processGreeting(long userId, String username, String firstname){
        String greet = getGreeting();

        Document logDoc = new Document("userId", userId).append("username", username).append("firstName", firstname)
                .append("input", "/start").append("response", greet).append("state", "IDLE").append("time", new Date());
        MongoDB.saveLog(logDoc);

        return greet;
    }

    public String getGreeting(){
        return "=== Чат-бот запущен ===\n" +
                "Напишите 'помощь' для списка команд или 'выход' для завершения.\n";
    }

    public String questionInput(String input){
        return questionInput(1L, input); // Используем тестовый ID 1
    }


    public String questionInput(long userId, String input){
        String question = input.trim().toLowerCase();

        if (question.isEmpty()) {
            return "Вы ничего не ввели, попробуйте ещё раз.";
        }


        for (BotCommand cmd : dataBaseBot.getAllCommands()) {
            if (cmd.isWaitingForInput(context, userId)) {
                return cmd.processDialog(context, userId, input);
            }
        }


        BotCommand command = dataBaseBot.findCommand(question);
        if (command == null) {
            return "Я не знаю ответа на этот вопрос.";
        }

        return command.execute(context, userId, input);
    }
}