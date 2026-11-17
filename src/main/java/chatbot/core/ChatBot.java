package chatbot.core;

import chatbot.core.command.BotCommand;

public class ChatBot {

    private final DataBaseBot dataBaseBot;
    private final BotContext context;
    private final MongoDB mongoDB;

    public ChatBot(DataBaseBot dataBaseBot, MongoDB mongoDB){
        this.dataBaseBot = dataBaseBot;
        this.context = new BotContext();
        this.mongoDB = mongoDB;
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

        mongoDB.saveProcessMessage(userId, username, firstname, input, res, state);

        return res;
    }

    public String processGreeting(long userId, String username, String firstname){
        String greet = getGreeting();

        mongoDB.saveProcessGreeting(userId, username, firstname, greet);

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