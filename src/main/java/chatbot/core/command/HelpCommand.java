package chatbot.core.command;
import chatbot.core.BotContext;
import chatbot.core.DataBaseBot;

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
