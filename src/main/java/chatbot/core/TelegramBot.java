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

        String userMessage = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        if (userMessage.equals("/start")) {
            sendMessage(chatId, chatBot.getGreeting());
            return;
        }

        String botResponse = chatBot.questionInput(userMessage);
        sendMessage(chatId, botResponse);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}
