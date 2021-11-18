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
public class MainMenuService implements MenuBuilder {
// TODO: тут формируется главное меню

    public SendMessage getMessage(long chatId) {
        return buildMenuMessage(chatId);
    }

    @Override
    public SendMessage buildMenuMessage(long chatId) {
        SendMessage reply = new SendMessage();
        reply.enableMarkdown(true);
        reply.setChatId(String.valueOf(chatId));
        return reply;
    }

    @Override
    public ReplyKeyboardMarkup getKeyBoard() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        // тут приветственное сообщение
        row.add(new KeyboardButton("Заглушка-заглушечка темные очи, я люблю тебя заглушечка очень"));
        row1.add(new KeyboardButton("Хоккей"));
        row2.add(new KeyboardButton("Помощь"));
        row3.add(new KeyboardButton("Мои подписки"));
        keyboard.add(row);
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        markup.setKeyboard(keyboard);
        return markup;
    }
}
