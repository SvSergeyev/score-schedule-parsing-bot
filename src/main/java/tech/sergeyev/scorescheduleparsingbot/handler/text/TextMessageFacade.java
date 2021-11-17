package tech.sergeyev.scorescheduleparsingbot.handler.text;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import tech.sergeyev.scorescheduleparsingbot.bot.BotStates;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class TextMessageFacade {
    static final Logger LOGGER = LoggerFactory.getLogger(TextMessageFacade.class);

    final Map<BotStates, DefaultTextMessageHandler> handlers = new HashMap<>();

    public TextMessageFacade(List<DefaultTextMessageHandler> handlers) {
        for (DefaultTextMessageHandler handler : handlers) {
            LOGGER.info("Handler: " + handler.getName());
            this.handlers.put(handler.getName(), handler);
        }
    }

    public SendMessage processMessage(BotStates state, Message message) {
        DefaultTextMessageHandler currentHandler = findHandler(state);
        return currentHandler.handle(message);
    }

    public DefaultTextMessageHandler findHandler(BotStates state) {
        switch (state) {
            case SHOW_MAIN:
                return handlers.get(BotStates.SHOW_MAIN);
            case SHOW_SUBS:
                return handlers.get(BotStates.SHOW_SUBS);
            case SHOW_HELP:
            default:
                return handlers.get(BotStates.SHOW_HELP);
        }
    }
}
