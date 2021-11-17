package tech.sergeyev.scorescheduleparsingbot.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import tech.sergeyev.scorescheduleparsingbot.bot.BotStates;
import tech.sergeyev.scorescheduleparsingbot.cache.UserCache;
import tech.sergeyev.scorescheduleparsingbot.handler.callback.CallbackFacade;
import tech.sergeyev.scorescheduleparsingbot.handler.text.TextMessageFacade;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class DefaultFacade {

    final CallbackFacade callbackHandler;
    final TextMessageFacade textMessageHandler;
    final UserCache cache;

    public DefaultFacade(CallbackFacade callbackHandler,
                         TextMessageFacade textMessageHandler,
                         UserCache cache) {
        this.callbackHandler = callbackHandler;
        this.textMessageHandler = textMessageHandler;
        this.cache = cache;
    }

    public SendMessage handleUpdate(Update update) {
        return update.hasCallbackQuery()
                ? handleCallbackQuery(update.getCallbackQuery())
                : handleTextMessage(update.getMessage());
    }

    private SendMessage handleTextMessage(Message message) {
        BotStates state;

        String text = message.getText();
        long userId = message.getFrom().getId();

        switch (text) {
            case "/start":
            case "/main":
                state = BotStates.SHOW_MAIN;
                break;
            case "/subs":
                state = BotStates.SHOW_SUBS;
                break;
            case "/help":
            default:
                // TODO: вот тут бы по-хорошему вколхозить обработчик любого текста,
                //  чтобы не надо было тыкать в кнопку с названием клуба,
                //  а просто писать название и получать такой же результат, как и с кнопки
                // а пока это просто "/help"
                // но только пока
                state = BotStates.SHOW_HELP;
        }

        cache.addBotStateForUser(userId, state);
        return textMessageHandler.processMessage(state, message);
    }

    private SendMessage handleCallbackQuery(CallbackQuery query) {
        return callbackHandler.processCallbackQuery(query);
    }
}
