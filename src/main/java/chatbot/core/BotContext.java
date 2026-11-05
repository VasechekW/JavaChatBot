package chatbot.core;

import chatbot.core.AviasalesTickets.Airport;
import chatbot.core.AviasalesTickets.UserState;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BotContext {
    private final Map<Long, UserState> userStates = new ConcurrentHashMap<>();
    private final Map<Long, Airport> userOrigins = new ConcurrentHashMap<>();

    public UserState getState(long userId) {
        return userStates.getOrDefault(userId, UserState.IDLE);
    }

    public void setState(long userId, UserState state) {
        userStates.put(userId, state);
    }

    public Airport getOrigin(long userId) {
        return userOrigins.get(userId);
    }

    public void setOrigin(long userId, Airport origin) {
        userOrigins.put(userId, origin);
    }
}