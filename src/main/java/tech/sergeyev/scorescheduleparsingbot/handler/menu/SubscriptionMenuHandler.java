package tech.sergeyev.scorescheduleparsingbot.handler.menu;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import tech.sergeyev.scorescheduleparsingbot.bot.BotStates;
import tech.sergeyev.scorescheduleparsingbot.handler.text.DefaultTextMessageHandler;

public class SubscriptionMenuHandler implements DefaultTextMessageHandler {
    @Override
    public SendMessage handle(Message message) {
        return null;
    }

    @Override
    public BotStates getName() {
        return null;
    }
}
