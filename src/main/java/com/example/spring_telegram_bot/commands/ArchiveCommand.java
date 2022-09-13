package com.example.spring_telegram_bot.commands;

import com.example.spring_telegram_bot.models.OrderModel;
import com.example.spring_telegram_bot.models.UserModel;
import com.example.spring_telegram_bot.repos.OrderRepo;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;
import java.util.List;

@Component
public class ArchiveCommand implements CommandWorker{

    final OrderRepo orderRepo;

    public ArchiveCommand(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public SendMessage execute(Update update) {
        if (!update.getMessage().getText().equals("Просмотреть архив")) {
            return null;
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());

        List<OrderModel> list = orderRepo.findAllByIfAnswered(true);

        StringBuilder stringBuilder = new StringBuilder();
        if (list.isEmpty()){
            stringBuilder.append("There is no orders");
        }else {
            for (OrderModel o : list) {
                stringBuilder.append(o.getCarName() + "\n");
                stringBuilder.append(o.getName() + "\n");
                stringBuilder.append(o.getContact() + "\n" + "\n");
            }
        }

        KeyboardRow keyboardRow = new KeyboardRow();
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        keyboardRow.add(new KeyboardButton("Главная"));
        replyKeyboardMarkup.
                setKeyboard(Collections.singletonList(keyboardRow));
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        sendMessage.setText(stringBuilder.toString());
        return sendMessage;
    }
}
