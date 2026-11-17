package chatbot.core;

import chatbot.core.command.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataBaseBot {

    private final Map<String, BotCommand> commands = new HashMap<>();

    public DataBaseBot(MongoDB db){
        commands.put("информация о городе", new CityInfoCommand(db));
        commands.put("авиабилеты", new AviaTicketsCommand());
        commands.put("история поиска", new LastTicketCommand(db));
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

    public Collection<BotCommand> getAllCommands() {
        return commands.values();
    }

    public Set<String> listQuestions() {
        return commands.keySet();
    }
}
