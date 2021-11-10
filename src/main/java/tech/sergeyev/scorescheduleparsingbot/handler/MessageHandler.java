package tech.sergeyev.scorescheduleparsingbot.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import tech.sergeyev.scorescheduleparsingbot.bot.BotStates;
import tech.sergeyev.scorescheduleparsingbot.cache.UserCache;

@Component
public class MessageHandler implements DefaultIncomingMessageHandler {
    private final UserCache userCache;

    public MessageHandler(UserCache userCache) {
        this.userCache = userCache;
    }

    @Override
    public SendMessage handle(Update update) {
        long userId = update.getMessage().getFrom().getId();

        if (userCache.getCurrentBotStateForUser(userId) == null) {
            userCache.addBotStateForUser(userId, BotStates.MAIN_MENU);
        }

        if (!update.getMessage().hasText()) {
            return new SendMessage(
                    String.valueOf(update.getMessage().getChatId()),
                    "Сообщение без текста. Попробуйте написать что-нибудь, например \"хоккей\"");
        }

        //TODO: здесь как-то должны обрабатываться текстовые команды

        return new SendMessage(
                String.valueOf(update.getMessage().getChatId()),
                "В будущем я смогу поискать что-нибудь про \"" +
                        update.getMessage().getText() +
                        "\", а пока я не понимаю текстовые сообщения." +
                        " Воспользуйтесь кнопками, находящимся под сообщениями в чате");
    }
}
