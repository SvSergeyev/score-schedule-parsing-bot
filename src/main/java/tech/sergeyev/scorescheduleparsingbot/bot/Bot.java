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
import tech.sergeyev.scorescheduleparsingbot.handler.DefaultFacade;
import tech.sergeyev.scorescheduleparsingbot.parser.Parser;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@NoArgsConstructor
@PropertySource("classpath:bot.properties")
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class Bot extends TelegramWebhookBot {
    final Logger LOGGER = LoggerFactory.getLogger(Bot.class);

    final List<Parser> parsers = new ArrayList<>();
    Parser hockeyParser;
    DefaultFacade facade;

    @Value("${bot.Username}")
    String botUsername;
    @Value("${bot.Token}")
    String botToken;
    @Value("${bot.Path}")
    String botPath;
    @Value("${bot.Admin}")
    String ADMIN_CHAT;

    @Autowired
    public Bot(@Qualifier("hockeyParser") Parser hockeyParser,
               DefaultFacade facade) {
        this.hockeyParser = hockeyParser;
        parsers.add(hockeyParser);

        this.facade = facade;
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
        parsers.forEach(Parser::start);
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update != null) {
            return facade.handleUpdate(update);
        } else
            return sendWarningMessageToAdmin();
    }

    private SendMessage sendWarningMessageToAdmin() {
        LOGGER.warn("Incoming Update object is null");
        SendMessage message = new SendMessage();
        message.setChatId(ADMIN_CHAT);
        message.setText("I cannot process the incoming Update class object");
        return message;
    }
}

