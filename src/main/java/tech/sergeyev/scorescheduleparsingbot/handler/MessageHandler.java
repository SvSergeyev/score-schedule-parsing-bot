package tech.sergeyev.scorescheduleparsingbot.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import tech.sergeyev.scorescheduleparsingbot.bot.BotState;
import tech.sergeyev.scorescheduleparsingbot.cache.UserCache;

@Component
public final class MessageHandler implements DefaultHandler{
    private final UserCache userCache;

    public MessageHandler(UserCache userCache) {
        this.userCache = userCache;
    }

    @Override
    public SendMessage handle(Update update) {
        String message = update.getMessage().getText().toLowerCase();
        long userId = update.getMessage().getFrom().getId();
        BotState botState;
        if (message.equals(ListOfCyrillicTextCommands.HOCKEY.getCyrillicText())) {
            botState = BotState.HOCKEY;
        }
        else if (message.equals(ListOfCyrillicTextCommands.FORMULA_1.getCyrillicText())) {
            botState = BotState.FORMULA;
        }
        else {
            botState = userCache.getCurrentBotStateForUser(userId);
        }
        return null;
    }
}
