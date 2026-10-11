package main.java.chatbot.core.command;
import main.java.chatbot.core.BotContext;
import main.java.chatbot.core.DataBaseBot;

public class HelpCommand implements BotCommand{

    private final DataBaseBot dataBaseBot;

    public HelpCommand(DataBaseBot dataBaseBot) {
        this.dataBaseBot = dataBaseBot;
    }

    @Override
    public String execute(BotContext context, String argument) {
        return "Доступные команды: " + String.join(", ", dataBaseBot.listQuestions());
    }
}
