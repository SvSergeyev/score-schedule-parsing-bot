package tech.sergeyev.scorescheduleparsingbot.handler;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public final class ReplyFacade implements DefaultHandler{
    MessageHandler messageHandler;
    CallbackHandler callbackHandler;

    public ReplyFacade(MessageHandler messageHandler, CallbackHandler callbackHandler) {
        this.messageHandler = messageHandler;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public SendMessage handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleTextMessage(update);
        }
        else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update);
        }
        return null;
    }

    private SendMessage handleTextMessage(Update update) {
        return messageHandler.handle(update);
    }

    private SendMessage handleCallbackQuery(Update update) {
        return callbackHandler.handle(update);
    }


}
