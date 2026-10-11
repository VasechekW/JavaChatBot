package main.java.chatbot.core.command;

import main.java.chatbot.core.BotContext;

public interface BotCommand {
    String execute(BotContext context, String argument);
}