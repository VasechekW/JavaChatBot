package chatbot.core.command;

import chatbot.core.AviasalesTickets.CityDict;
import chatbot.core.AviasalesTickets.ParsedTicket;
import chatbot.core.AviasalesTickets.TicketParser;
import chatbot.core.BotContext;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static chatbot.core.AviasalesTickets.HttpResponseJson.getCheapTickets;

public class AviaTicketsCommand implements BotCommand {


    @Override
    public String execute(BotContext context, String argument) {

        String tomorrow = LocalDate.now().plusDays(1).toString();
        String origin = "SVX";

        String json = getCheapTickets(origin, "-", tomorrow);
        List<ParsedTicket> tickets = TicketParser.parse(json, origin);

        if (tickets.isEmpty()) {
            return "Дешёвых билетов в кэше нет. Попробуйте другую дату.";
        }

        tickets.sort(Comparator.comparingInt(t -> t.getInfo().getPrice()));

        StringBuilder sb = new StringBuilder();
        sb.append("Билеты из ").append(CityDict.name(origin))
                .append(" на ").append(tomorrow).append(":\n\n");


        int count = 0;
        for (ParsedTicket t: tickets){
            if (count >= 40) break;
            sb.append(t).append("\n");
            count ++;
        }

        return sb.toString();

    }

}