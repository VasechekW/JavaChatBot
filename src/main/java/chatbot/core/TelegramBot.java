package chatbot.core;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {
    private final ChatBot chatBot;
    private final String botUsername;

    public TelegramBot(String botToken, String botUsername) {
        super(botToken);
        this.botUsername = botUsername;
        this.chatBot = new ChatBot(new DataBaseBot());
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = SendMessage.builder().chatId(String.valueOf(chatId)).text(text).build();
        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.err.println("Ошибка отправки " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        long chatId = update.getMessage().getChatId();
        long userId = update.getMessage().getFrom().getId();
        String username = update.getMessage().getFrom().getUserName();
        String firstname = update.getMessage().getFrom().getFirstName();
        String userMessage = update.getMessage().getText();

        if (userMessage.equals("/start")) {
            sendMessage(chatId, chatBot.processGreeting(userId, username, firstname));
            return;
        }

        String botResponse = chatBot.processMessage(userId, username, firstname, userMessage);
        sendMessage(chatId, botResponse);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}