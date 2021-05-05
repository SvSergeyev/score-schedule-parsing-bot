package tech.sergeyev.scorescheduleparsingbot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class SimpleAnswerService {
    public SendMessage reply;
}
