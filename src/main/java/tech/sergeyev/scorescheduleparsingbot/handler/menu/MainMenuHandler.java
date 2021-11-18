package tech.sergeyev.scorescheduleparsingbot.handler.menu;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import tech.sergeyev.scorescheduleparsingbot.bot.BotStates;
import tech.sergeyev.scorescheduleparsingbot.handler.text.DefaultTextMessageHandler;
import tech.sergeyev.scorescheduleparsingbot.service.MainMenuService;

@Component
public class MainMenuHandler implements DefaultTextMessageHandler {
    private final BotStates name = BotStates.SHOW_MAIN;
    private final MainMenuService mainMenuService;

    public MainMenuHandler(MainMenuService mainMenuService) {
        this.mainMenuService = mainMenuService;
    }

    @Override
    public SendMessage handle(Message message) {
        return mainMenuService.getMessage(message.getChatId());
    }

    @Override
    public BotStates getName() {
        return name;
    }
}
