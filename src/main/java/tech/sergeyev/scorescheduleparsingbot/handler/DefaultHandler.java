package tech.sergeyev.scorescheduleparsingbot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface DefaultHandler {
    SendMessage handle(Update update);
}
