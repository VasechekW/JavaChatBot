package chatbot.core.AviasalesTickets;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpResponseJson {


    private static final String TOKEN = System.getenv("TRAVELPAYOUTS_TOKEN");

    public static String getCheapTickets(String origin, String destination, String date) {
        String url = "https://api.travelpayouts.com/v1/prices/cheap"
                + "?origin=" + origin
                + "&destination=" + destination
                + "&depart_date=" + date;

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

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
            return response.body();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при запросе к Aviasales API", e);
        }
    }
}