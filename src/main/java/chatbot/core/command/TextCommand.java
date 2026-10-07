package main.java.chatbot.core.command;
import main.java.chatbot.core.BotContext;

public class TextCommand implements BotCommand{

    private final String answer;

    public TextCommand(String answer){
        this.answer = answer;
    }

    @Override
    public String execute(BotContext context, String argument){
        return answer;
    }
}
