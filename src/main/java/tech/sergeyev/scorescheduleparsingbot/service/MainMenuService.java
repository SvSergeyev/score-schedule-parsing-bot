package tech.sergeyev.scorescheduleparsingbot.service;

import org.checkerframework.checker.units.qual.K;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainMenuService {
// TODO: тут формируется главное меню

    public SendMessage getMainMenuMessage() {
        SendMessage mainMenuMessage = new SendMessage();

        return mainMenuMessage;
    }

    private ReplyKeyboardMarkup getMainMenuKeyboard() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow hockeyRow = new KeyboardRow();
        KeyboardRow helpRow = new KeyboardRow();
        KeyboardRow mySubscribesRow = new KeyboardRow();
        hockeyRow.add(new KeyboardButton("Хоккей"));
        helpRow.add(new KeyboardButton("Помощь"));
        mySubscribesRow.add(new KeyboardButton("Мои подписки"));
        keyboard.add(hockeyRow);
        keyboard.add(helpRow);
        keyboard.add(mySubscribesRow);
        markup.setKeyboard(keyboard);
        return markup;
    }
}
