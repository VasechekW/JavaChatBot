package chatbot.core.command;

import chatbot.core.BotContext;

public interface BotCommand {

    String execute(BotContext context, long userId, String argument);


    default boolean isWaitingForInput(BotContext context, long userId) {
        return false;
    }

    default String processDialog(BotContext context, long userId, String text) {
        return "Ошибка: команда не поддерживает диалог.";
    }
}