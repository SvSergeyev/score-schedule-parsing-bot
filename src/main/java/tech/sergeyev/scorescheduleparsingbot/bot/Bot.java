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
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import tech.sergeyev.scorescheduleparsingbot.parser.CalendarPageParser;
import tech.sergeyev.scorescheduleparsingbot.parser.Parser;
import tech.sergeyev.scorescheduleparsingbot.parser.clubs.ClubsPageParser;

import javax.annotation.PostConstruct;
import java.io.IOException;
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
    Parser clubsParser;
    Parser calendarParser;

    @Value("${bot.Username}")
    String botUsername;
//    @Value("${bot.Token}")
    final String botToken = System.getenv("TOKEN");
    @Value("${bot.Path}")
    String botPath;

    @Autowired
    public Bot(@Qualifier("clubsPageParser") ClubsPageParser clubsParser,
               @Qualifier("calendarPageParser") CalendarPageParser calendarParser) {
        this.clubsParser = clubsParser;
        this.calendarParser = calendarParser;
        parsers.add(clubsParser);
        parsers.add(calendarParser);
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
        LOGGER.info("runAllParsersMethod: started");
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
        LOGGER.info("Get message from " + reply.getChatId() + " with text: " + reply.getText());
        try {
            execute(reply);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return reply;
    }
}

