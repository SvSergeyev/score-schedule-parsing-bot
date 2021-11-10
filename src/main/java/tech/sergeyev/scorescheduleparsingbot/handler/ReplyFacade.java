package tech.sergeyev.scorescheduleparsingbot.handler;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import tech.sergeyev.scorescheduleparsingbot.bot.BotContext;
import tech.sergeyev.scorescheduleparsingbot.bot.BotStates;
import tech.sergeyev.scorescheduleparsingbot.parser.hockey.clubs.TeamsAbbreviations;

import java.util.Arrays;
import java.util.List;

@Service
public final class ReplyFacade {
    private final MessageHandler messageHandler;
    private final CallbackHandler callbackHandler;
    private final BotContext context;

    public ReplyFacade(MessageHandler messageHandler,
                       CallbackHandler callbackHandler,
                       BotContext context) {
        this.messageHandler = messageHandler;
        this.callbackHandler = callbackHandler;
        this.context = context;
    }

    public SendMessage defineHandlerAndProcess(Update update) {
        return update.hasCallbackQuery() ? handleCallbackQuery(update) : handleTextMessage(update);
    }

    private SendMessage handleTextMessage(Update update) {
        BotStates state;

        String text = update.getMessage().getText();
        // TODO: криво, надо поменять порядок работы, в идеале через switch
        for (TeamsAbbreviations team : TeamsAbbreviations.values()) {
            if (team.getKeyName().equalsIgnoreCase(text.substring(0, 2))) {
//                if (text.substring(0, 2).equals("дин")) {
                //TODO: тут можно подумать, как вытащить город из запроса
//                }
                state = BotStates.HOCKEY_TEAM_CHANGE;
                break;
            }
        }
            if (text.equalsIgnoreCase("помощь")) {
                state = BotStates.SHOW_HELP;
            }
            else if (text.equalsIgnoreCase("подписки")) {
                state = BotStates.SHOW_SUBSCRIBES;
            }
            else {
                state = BotStates.SHOW_HELP;
            }

//        switch (text) {
//            case Arrays.stream(TeamsAbbreviations.values())
//                    .map(TeamsAbbreviations::getKeyName)
//                    .anyMatch(text.equalsIgnoreCase(TeamsAbbreviations::getKeyName):
//            case "помощь":
//                state = BotStates.SHOW_HELP;
//                break;
//            case "подписки":
//                state = BotStates.SHOW_SUBSCRIBES;
//                break;
//            default: state = BotStates.SHOW_HELP;
//        }

        return context.processMessage(state, update.getMessage());
//        return messageHandler.handle(update);
    }

    private SendMessage handleCallbackQuery(Update update) {
        return callbackHandler.handle(update);
    }
}
