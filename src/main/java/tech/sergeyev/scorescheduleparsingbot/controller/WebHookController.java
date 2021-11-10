package tech.sergeyev.scorescheduleparsingbot.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import tech.sergeyev.scorescheduleparsingbot.bot.Bot;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebHookController {
    final Bot bot;

    public WebHookController(Bot bot) {
        this.bot = bot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return bot.onWebhookUpdateReceived(update);
    }
}
