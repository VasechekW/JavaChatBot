package chatbot.core.AviasalesTickets;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import io.github.cdimascio.dotenv.Dotenv;

public class TicketsApi {

    private final Dotenv DOTENV = Dotenv.load();
    private final String TOKEN = DOTENV.get("TRAVELPAYOUTS_TOKEN");
    private final HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();


    public List<Ticket> getCheapTickets(String origin, String destination, String date) {
        String url = "https://api.travelpayouts.com/v1/prices/cheap"
                + "?origin=" + origin
                + "&destination=" + destination
                + "&depart_date=" + date;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("x-access-token", TOKEN)
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );
            String json = response.body();
            return TicketParser.parse(json, origin);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при запросе к Aviasales API", e);
        }
    }
}