package tech.sergeyev.scorescheduleparsingbot.handler.callback;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface DefaultCallbackHandler {
    SendMessage handle(CallbackQuery query);
}
