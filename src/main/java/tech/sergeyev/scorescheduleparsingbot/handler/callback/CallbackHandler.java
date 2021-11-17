package tech.sergeyev.scorescheduleparsingbot.handler.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import tech.sergeyev.scorescheduleparsingbot.cache.UserCache;
import tech.sergeyev.scorescheduleparsingbot.service.MainMenuService;

@Component
public class CallbackHandler implements DefaultCallbackHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger(CallbackHandler.class);

    private final MainMenuService mainMenuService;
    private final UserCache cache;

    public CallbackHandler(MainMenuService mainMenuService,
                           UserCache cache) {
        this.mainMenuService = mainMenuService;
        this.cache = cache;
    }

    @Override
    public SendMessage handle(CallbackQuery query) {
        return null;
    }
}
