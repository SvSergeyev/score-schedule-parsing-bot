package tech.sergeyev.scorescheduleparsingbot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface MenuBuilder {
    SendMessage buildMenuMessage(long chatId);
    ReplyKeyboardMarkup getKeyBoard();
}
