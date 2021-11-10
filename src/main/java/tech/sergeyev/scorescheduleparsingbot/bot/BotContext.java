package tech.sergeyev.scorescheduleparsingbot.bot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import tech.sergeyev.scorescheduleparsingbot.handler.DefaultIncomingMessageHandler;

@Component
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class BotContext {

    public SendMessage processMessage(BotStates state, Message message) {
        return null;
    }

    public DefaultIncomingMessageHandler findHandler(BotStates state, Message message) {
        return null;
    }
}
