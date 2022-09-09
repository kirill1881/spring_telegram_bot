package com.example.spring_telegram_bot.bot;

import com.example.spring_telegram_bot.Helper;
import com.example.spring_telegram_bot.commands.ActualCommand;
import com.example.spring_telegram_bot.commands.CommandWorker;
import com.example.spring_telegram_bot.commands.MainCommand;
import com.example.spring_telegram_bot.models.UserModel;
import com.example.spring_telegram_bot.models.enums.StateEnum;
import com.example.spring_telegram_bot.repos.OrderRepo;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {


    @Autowired
    UserRpository userRpository;

    @Autowired
    OrderRepo orderRepo;

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

        List<CommandWorker> list = new ArrayList<>();
        list.add(new MainCommand(userRpository));
        list.add(new ActualCommand(orderRepo, userRpository));

        for (CommandWorker c: list){
           sendMessage = c.execute(update);
           if (sendMessage!=null){
               break;
           }
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
