package tech.sergeyev.scorescheduleparsingbot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface DefaultIncomingMessageHandler {
    SendMessage handle(Update update);
}
