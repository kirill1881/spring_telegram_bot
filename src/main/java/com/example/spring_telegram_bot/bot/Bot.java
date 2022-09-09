package com.example.spring_telegram_bot.bot;

import com.example.spring_telegram_bot.Helper;
import com.example.spring_telegram_bot.models.UserModel;
import com.example.spring_telegram_bot.models.enums.StateEnum;
import com.example.spring_telegram_bot.repos.UserRpository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;

@Component
public class Bot extends TelegramLongPollingBot {


    @Autowired
    UserRpository userRpository;

    @Override
    public String getBotUsername() {
        return "manger_overone_bot";
    }

    @Override
    public String getBotToken() {
        return "5465616449:AAH2J5N7RNPyJ2ZYXqFQqXzU-wtIgD2_WLI";
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();


        if (update.getMessage().getText().equals("/start")) {
            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setText("Hi, my dear manager");

            UserModel userModel = new UserModel();
            userModel.setName(update.getMessage().getFrom().getFirstName());
            userModel.setStateEnum(StateEnum.START);
            userModel.setTgId(update.getMessage().getFrom().getId().toString());

            userRpository.save(userModel);
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton("Посмотреть заявки"));
            keyboardRow.add(new KeyboardButton("Просмотреть архив"));

            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardRow));

            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
