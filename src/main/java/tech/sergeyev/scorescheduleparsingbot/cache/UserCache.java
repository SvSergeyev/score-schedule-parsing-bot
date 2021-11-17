package tech.sergeyev.scorescheduleparsingbot.cache;

import org.springframework.stereotype.Component;
import tech.sergeyev.scorescheduleparsingbot.bot.BotStates;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class UserCache implements DefaultCache {
    Map<Long, BotStates> allUsersBotStates = new HashMap<>();

    public void addBotStateForUser(long userId, BotStates state) {
        allUsersBotStates.put(userId, state);
    }

    public BotStates getCurrentBotStateForUser(long userId) {
        BotStates currentBotState = allUsersBotStates.get(userId);
        return Objects.requireNonNullElse(currentBotState, BotStates.SHOW_MAIN);
    }
}
