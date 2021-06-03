package tech.sergeyev.scorescheduleparsingbot.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CallbackHandler implements DefaultHandler {
    private Update update;


    @Override
    public SendMessage handle(Update update) {
        return null;
    }
}
