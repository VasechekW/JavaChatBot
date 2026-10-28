package chatbot.core.AviasalesTickets;

public record Ticket(String originCode, String originName, String destinationCode, String destinationName,
                     TicketInfo info) {

    @Override
    public String toString() {
        return String.format("%s (%s) -> %s (%s): %d₽, вылет %s, ав-ия %s",
                originName, originCode,
                destinationName, destinationCode,
                info.price(),
                info.departure_at(),
                info.airline());
    }
}