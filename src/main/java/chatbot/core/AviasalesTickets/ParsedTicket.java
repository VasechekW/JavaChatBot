package chatbot.core.AviasalesTickets;

public class ParsedTicket {
    private final String originCode;
    private final String originName;
    private final String destinationCode;
    private final String destinationName;
    private final TicketInfo info;         // цена, время, авиакомпания

    public ParsedTicket(String originCode, String originName,
                        String destinationCode, String destinationName,
                        TicketInfo info) {
        this.originCode = originCode;
        this.originName = originName;
        this.destinationCode = destinationCode;
        this.destinationName = destinationName;
        this.info = info;
    }

    public String getOriginCode() { return originCode; }
    public String getOriginName() { return originName; }
    public String getDestinationCode() { return destinationCode; }
    public String getDestinationName() { return destinationName; }
    public TicketInfo getInfo() { return info; }

    @Override
    public String toString() {
        return String.format("%s (%s) -> %s (%s): %d₽, вылет %s, ав-ия %s",
                originName, originCode,
                destinationName, destinationCode,
                info.getPrice(),
                info.getDepartureAt(),
                info.getAirline());
    }
}