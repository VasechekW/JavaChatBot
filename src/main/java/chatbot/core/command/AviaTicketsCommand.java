package chatbot.core.command;

import chatbot.core.AviasalesTickets.Airport;
import chatbot.core.AviasalesTickets.Ticket;
import chatbot.core.AviasalesTickets.TicketsApi;
import chatbot.core.BotContext;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class AviaTicketsCommand implements BotCommand {

    private final TicketsApi ticketsApi = new TicketsApi();

    @Override
    public String execute(BotContext context, String argument) {

        String tomorrow = LocalDate.now().plusDays(1).toString();
        String origin = "SVX";

        List<Ticket> tickets = ticketsApi.getCheapTickets(origin, "-", tomorrow);

        if (tickets.isEmpty()) {
            return "Дешёвых билетов в кэше нет. Попробуйте другую дату.";
        }

        tickets.sort(Comparator.comparingInt(t -> t.info().price()));

        StringBuilder sb = new StringBuilder();
        sb.append("Билеты из ").append(Airport.valueOf(origin).getCity())
                .append(" на ").append(tomorrow).append(":\n\n");


        int count = 0;
        for (Ticket t: tickets){
            if (count >= 40) break;
            sb.append(t).append("\n");
            count ++;
        }

        return sb.toString();

    }
}