package chatbot.ui;

import chatbot.core.TelegramBot;
import io.github.cdimascio.dotenv.Dotenv;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramLauncher {
    public static void main(String[] args) throws TelegramApiException {
        Dotenv dotenv = Dotenv.load();
        String botToken = dotenv.get("TELEGRAM_BOT_TOKEN");
        String botUsername = dotenv.get("TELEGRAM_BOT_USERNAME");

        if (botToken == null || botUsername == null) {
            System.err.println("Не заданы параметры в .env");
            return;
        }

        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(new TelegramBot(botToken, botUsername));
        System.out.println("Бот запущен: @" + botUsername);
    }
}
