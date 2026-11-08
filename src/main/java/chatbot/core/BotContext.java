package chatbot.core;

import chatbot.core.AviasalesTickets.Airport;
import chatbot.core.AviasalesTickets.UserState;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BotContext {

    public static class UserSession {

        private UserState state = UserState.IDLE; // По умолчанию IDLE
        private Airport origin = null;

        public UserState getState() { return state; }
        public void setState(UserState state) { this.state = state; }

        public Airport getOrigin() { return origin; }
        public void setOrigin(Airport origin) { this.origin = origin; }

    }
    private final Map<Long, UserSession> sessions = new ConcurrentHashMap<>();

    private UserSession getSession(long userId) {
        return sessions.computeIfAbsent(userId, ID -> new UserSession());
    }


    public UserState getState(long userId) {
        return getSession(userId).getState();
    }

    public void setState(long userId, UserState state) {
        getSession(userId).setState(state);
    }

    public Airport getOrigin(long userId) {
        return getSession(userId).getOrigin();
    }

    public void setOrigin(long userId, Airport origin) {
        getSession(userId).setOrigin(origin);
    }
}