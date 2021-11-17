package tech.sergeyev.scorescheduleparsingbot.handler.text;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import tech.sergeyev.scorescheduleparsingbot.bot.BotStates;

public interface DefaultTextMessageHandler {
    SendMessage handle(Message message);

    BotStates getName();
}
