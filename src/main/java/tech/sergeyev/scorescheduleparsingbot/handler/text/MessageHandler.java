package tech.sergeyev.scorescheduleparsingbot.handler.text;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import tech.sergeyev.scorescheduleparsingbot.bot.BotStates;
import tech.sergeyev.scorescheduleparsingbot.cache.UserCache;

@Component
public class MessageHandler implements DefaultTextMessageHandler {
    private final UserCache userCache;

    public MessageHandler(UserCache userCache) {
        this.userCache = userCache;
    }

    @Override
    public SendMessage handle(Message message) {
        long userId = message.getFrom().getId();

        if (userCache.getCurrentBotStateForUser(userId) == null) {
            userCache.addBotStateForUser(userId, BotStates.SHOW_MAIN);
        }

        if (!message.hasText()) {
            return new SendMessage(
                    String.valueOf(message.getChatId()),
                    "Сообщение без текста. Попробуйте написать что-нибудь, например \"хоккей\"");
        }

        //TODO: здесь как-то должны обрабатываться текстовые команды

        return new SendMessage(
                String.valueOf(message.getChatId()),
                "В будущем я смогу поискать что-нибудь про \"" +
                        message.getText() +
                        "\", а пока я не понимаю текстовые сообщения." +
                        " Воспользуйтесь кнопками, находящимся под сообщениями в чате");
    }
}
