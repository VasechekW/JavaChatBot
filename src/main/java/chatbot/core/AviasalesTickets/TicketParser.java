package chatbot.core.AviasalesTickets;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TicketParser {

    private static final Gson GSON = new Gson();

    public static List<Ticket> parse(String json, String originCode) {
        List<Ticket> result = new ArrayList<>();


        JsonObject root = JsonParser.parseString(json).getAsJsonObject();


        if (!root.has("success") || !root.get("success").getAsBoolean()) {
            System.err.println("API вернул success=false");
            return result;
        }


        if (!root.has("data") || root.get("data").isJsonNull()) {
            return result;
        }
        JsonObject data = root.getAsJsonObject("data");


        for (Map.Entry<String, JsonElement> cityEntry : data.entrySet()) {
            String destinationCode = cityEntry.getKey();
            JsonObject transfersObj = cityEntry.getValue().getAsJsonObject();


            for (Map.Entry<String, JsonElement> transferEntry : transfersObj.entrySet()) {
                JsonObject ticketJson = transferEntry.getValue().getAsJsonObject();


                TicketInfo info = GSON.fromJson(ticketJson, TicketInfo.class);


                Ticket ticket = new Ticket(
                        originCode,
                        Airport.getCityOrDefault(originCode),
                        destinationCode,
                        Airport.getCityOrDefault(destinationCode),
                        info
                );
                result.add(ticket);
            }
        }

        return result;
    }
}