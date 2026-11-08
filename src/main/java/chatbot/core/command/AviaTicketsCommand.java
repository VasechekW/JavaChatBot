package chatbot.core.command;

import chatbot.core.AviasalesTickets.Airport;
import chatbot.core.AviasalesTickets.Ticket;
import chatbot.core.AviasalesTickets.TicketsApi;
import chatbot.core.AviasalesTickets.UserState;
import chatbot.core.BotContext;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class AviaTicketsCommand implements BotCommand {

    private final TicketsApi ticketsApi = new TicketsApi();

    @Override
    public String execute(BotContext context, long userId, String argument) {

        context.setState(userId, UserState.WAITING_FOR_ORIGIN);
        return "Введите код или название города вылета (например, SVX):";
    }

    @Override
    public boolean isWaitingForInput(BotContext context, long userId) {
        UserState state = context.getState(userId);
        return state == UserState.WAITING_FOR_ORIGIN || state == UserState.WAITING_FOR_DATE;
    }

    @Override
    public String processDialog(BotContext context, long userId, String text) {
        UserState state = context.getState(userId);

        if (state == UserState.WAITING_FOR_ORIGIN) {
            Airport origin = Airport.fromString(text);
            if (origin == null) {
                return "Не узнал город. Попробуйте код аэропорта (например, SVX):";
            }
            context.setOrigin(userId, origin);
            context.setState(userId, UserState.WAITING_FOR_DATE);
            return "Город выбран: " + origin.getCity() + ".\nТеперь введите дату в формате ГГГГ-ММ-ДД:";
        }

        if (state == UserState.WAITING_FOR_DATE) {
            try {
                LocalDate.parse(text);
            } catch (Exception e) {
                return "Неверный формат. Введите дату ГГГГ-ММ-ДД (например, 2026-11-10):";
            }

            Airport origin = context.getOrigin(userId);
            context.setState(userId, UserState.IDLE); // Сбрасываем диалог
            return searchAndFormat(origin, text);
        }

        return "Ошибка состояния. Напишите /помощь.";
    }

    private String searchAndFormat(Airport origin, String date) {
        List<Ticket> tickets = ticketsApi.getCheapTickets(origin, "-", date);
        if (tickets.isEmpty()) {
            return "Дешёвых билетов из " + origin.getCity() + " на " + date + " в кэше нет.";
        }

        tickets.sort(Comparator.comparingInt(t -> t.info().price()));
        StringBuilder sb = new StringBuilder();
        sb.append("Билеты из ").append(origin.getCity()).append(" на ").append(date).append(":\n\n");

        int count = 0;
        for (Ticket t : tickets) {
            if (count >= 40) break;
            sb.append(t).append("\n");
            count++;
        }
        return sb.toString();
    }
}