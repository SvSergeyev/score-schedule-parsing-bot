package tech.sergeyev.scorescheduleparsingbot.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import tech.sergeyev.scorescheduleparsingbot.bot.BotStates;
import tech.sergeyev.scorescheduleparsingbot.cache.UserCache;
import tech.sergeyev.scorescheduleparsingbot.service.MainMenuService;

@Component
public class CallbackHandler implements DefaultIncomingMessageHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger(CallbackHandler.class);

    private final MainMenuService mainMenuService;
    private final UserCache cache;

    public CallbackHandler(MainMenuService mainMenuService,
                           UserCache cache) {
        this.mainMenuService = mainMenuService;
        this.cache = cache;
    }

    @Override
    public SendMessage handle(Update update) {
        long userId = update.getMessage().getFrom().getId();
        CallbackQuery query = update.getCallbackQuery();

        LOGGER.info("UPDATE.TO_STRING: " + update.toString());
        LOGGER.info("QUERY: " + query);

        if (cache.getCurrentBotStateForUser(userId) == null) {
            cache.addBotStateForUser(userId, BotStates.MAIN_MENU);
            return mainMenuService.getMainMenuMessage();
        }
        // TODO: здесь перебираем пункты меню: выбор команды, счет, расписание, подписка
        return null;
    }
}
