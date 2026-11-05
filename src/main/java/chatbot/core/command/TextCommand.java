package chatbot.core.command;

import chatbot.core.BotContext;

public class TextCommand implements BotCommand {

    private final String answer;

    public TextCommand(String answer) {
        this.answer = answer;
    }

    @Override
    public String execute(BotContext context, long userId, String argument) {
        return answer;
    }
}