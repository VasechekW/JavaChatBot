package chatbot.core;

import chatbot.core.command.BotCommand;

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