package com.example.spring_telegram_bot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandWorker {
    SendMessage execute(Update update);
}
