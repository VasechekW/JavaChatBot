package chatbot.core.command;

import chatbot.core.BotContext;
import chatbot.core.MongoDB;
import org.bson.Document;

public class LastTicketCommand implements BotCommand {

    @Override
    public String execute(BotContext context, long userId, String argument) {
        Document lastSearchLog = MongoDB.logs()
                .find(new Document("userId", userId).append("stateBefore", "WAITING_FOR_DATE"))
                .sort(new Document("timestamp", -1))
                .first();

        if (lastSearchLog == null) {
            return "Я не нашел в истории ваших запросов поиск авиабилетов.";
        }

        String botResponse = lastSearchLog.getString("response");

        if (botResponse == null || botResponse.isEmpty()) {
            return "Последний запрос билетов был, но список билетов пуст.";
        }

        return "Ваш последний запрос авиабилетов:\n\n" + botResponse;
    }
}