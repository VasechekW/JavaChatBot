package chatbot.core.command;

import chatbot.core.BotContext;
import chatbot.core.MongoDB;
import chatbot.core.AviasalesTickets.UserState;
import org.bson.Document;

import java.util.Locale;

public class CityInfoCommand implements BotCommand {

    @Override
    public String execute(BotContext context, long userId, String argument) {
        context.setState(userId, UserState.WAITING_FOR_CITY);
        return "Введите название города, о котором хотите узнать информацию (например, Екатеринбург):";
    }

    @Override
    public boolean isWaitingForInput(BotContext context, long userId) {
        return context.getState(userId) == UserState.WAITING_FOR_CITY;
    }

    @Override
    public String processDialog(BotContext context, long userId, String text) {
        String cityName = text.trim().toLowerCase();

        Document cityDoc = MongoDB.cities().find(new Document("name", cityName)).first();

        context.setState(userId, UserState.IDLE);

        if (cityDoc == null) {
            return "Извините, информация о городе \"" + cityName + "\" не найдена в базе данных.";
        }

        String name = cityDoc.getString("name");
        String info = cityDoc.getString("info");

        return name + "\n" + info;
    }
}