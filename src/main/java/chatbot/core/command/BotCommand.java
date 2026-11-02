package chatbot.core.command;

import chatbot.core.BotContext;

public interface BotCommand {
    String execute(BotContext context, String argument);
}