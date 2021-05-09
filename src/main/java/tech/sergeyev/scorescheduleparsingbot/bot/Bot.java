package tech.sergeyev.scorescheduleparsingbot.bot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import tech.sergeyev.scorescheduleparsingbot.parser.hockey.calendar.CalendarPageParser;
import tech.sergeyev.scorescheduleparsingbot.parser.Parser;
import tech.sergeyev.scorescheduleparsingbot.parser.hockey.clubs.ClubsPageParser;
import tech.sergeyev.scorescheduleparsingbot.parser.hockey.upgradable.UpgradableBlockParser;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@NoArgsConstructor
@PropertySource("classpath:bot.properties")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bot extends TelegramWebhookBot {
    final Logger LOGGER = LoggerFactory.getLogger(Bot.class);

    final List<Parser> parsers = new ArrayList<>();
    Parser hockeyParser;

    @Value("${bot.Username}")
    String botUsername;
    @Value("${bot.Token}")
    String botToken;
    @Value("${bot.Path}")
    String botPath;
    @Value("${bot.Admin}")
    String ADMIN_CHAT;

    @Autowired
    public Bot(@Qualifier("hockeyParser") Parser hockeyParser) {
        this.hockeyParser = hockeyParser;
        parsers.add(hockeyParser);
    }

    @PostConstruct
    private void registrationOfWebHook() {
        Content getResult = null;
        try {
            getResult = Request
                    .Get("https://api.telegram.org/bot" + this.getBotToken()
                            + "/setWebhook?url=" + this.getBotPath())
                    .execute().returnContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (getResult != null) {
            LOGGER.info(getResult.asString());
        } else {
            LOGGER.error("Webhook is not registered");
        }
    }

    @PostConstruct
    private void runAllParsers() {
        for (Parser parser : parsers) {
            parser.start();
        }
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return null;
        }
        String text = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        SendMessage reply = new SendMessage();
        reply.setChatId(String.valueOf(chatId));
        reply.setText(text);
        LOGGER.info(LocalDateTime.now() + ": Get message from: " + reply.getChatId() + " with text: " + reply.getText());
        return reply;
    }
}

