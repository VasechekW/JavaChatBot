package main.java.chatbot.core;

import main.java.chatbot.core.command.BotCommand;
import main.java.chatbot.core.command.TextCommand;
import main.java.chatbot.core.command.HelpCommand;

import java.util.HashMap;
import java.util.Map;

public class DataBaseBot {

    private final Map<String, BotCommand> commands = new HashMap<>();

    public DataBaseBot(){
        commands.put("помощь", new HelpCommand(this));
        commands.put("привет", new TextCommand("Привет! Чем могу помочь?"));
        commands.put("как дела", new TextCommand("Отлично, спасибо! А у тебя?)"));
        commands.put("кто ты", new TextCommand("Я простой чат-бот на Java."));
        commands.put("пока", new TextCommand("До встречи!"));

    }

    public BotCommand findCommand(String question) {
        if (question == null || question.isEmpty()) {
            return null;
        }
        return commands.get(question.toLowerCase());
    }

    public java.util.Set<String> listQuestions() {
        return commands.keySet();
    }
}
