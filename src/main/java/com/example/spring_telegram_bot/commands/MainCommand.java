package com.example.spring_telegram_bot.commands;

import com.example.spring_telegram_bot.bot.Bot;
import com.example.spring_telegram_bot.models.UserModel;
import com.example.spring_telegram_bot.models.enums.StateEnum;
import com.example.spring_telegram_bot.repos.UserRpository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;

@Component
public class MainCommand implements CommandWorker {

    final
    UserRpository userRpository;

    public MainCommand(UserRpository userRpository) {
        this.userRpository = userRpository;
    }

    @Override
    public SendMessage execute(Update update) {
        if (!update.getMessage().getText().equals("/start")&&
        !update.getMessage().getText().equals("Главная")){
            return null;
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("Hi, my dear manager");

        if (userRpository.findByTgId(update.getMessage().getFrom()
                .getId().toString())==null) {
            UserModel userModel = new UserModel();
            userModel.setName(update.getMessage().getFrom().getFirstName());
            userModel.setStateEnum(StateEnum.START);
            userModel.setTgId(update.getMessage().getFrom().getId().toString());

            userRpository.save(userModel);
        }

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton("Посмотреть заявки"));
        keyboardRow.add(new KeyboardButton("Просмотреть архив"));

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardRow));

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
}
