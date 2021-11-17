package tech.sergeyev.scorescheduleparsingbot.handler.callback;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public final class CallbackFacade {

    public SendMessage processCallbackQuery(CallbackQuery query) {
        return null;
    }
}
