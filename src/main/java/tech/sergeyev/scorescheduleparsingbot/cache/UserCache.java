package tech.sergeyev.scorescheduleparsingbot.cache;

import org.springframework.stereotype.Component;
import tech.sergeyev.scorescheduleparsingbot.bot.BotState;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class UserCache implements DefaultCache {
    Map<Long, BotState> allUsersBotStates = new HashMap<>();

    public void addBotStateForUser(long userId, BotState state) {
        allUsersBotStates.put(userId, state);
    }

    public BotState getCurrentBotStateForUser(long userId) {
        BotState currentBotState = allUsersBotStates.get(userId);
        return Objects.requireNonNullElse(currentBotState, BotState.MAIN_MENU);
    }
}
