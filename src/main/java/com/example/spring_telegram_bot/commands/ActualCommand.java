package com.example.spring_telegram_bot.commands;

import com.example.spring_telegram_bot.models.OrderModel;
import com.example.spring_telegram_bot.models.UserModel;
import com.example.spring_telegram_bot.models.enums.StateEnum;
import com.example.spring_telegram_bot.repos.OrderRepo;
import com.example.spring_telegram_bot.repos.UserRpository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;
import java.util.List;

@Component
public class ActualCommand implements CommandWorker {

    final OrderRepo orderRepo;
    final UserRpository userRpository;

    public ActualCommand(OrderRepo orderRepo, UserRpository userRpository) {
        this.orderRepo = orderRepo;
        this.userRpository = userRpository;
    }

    @Override
    public SendMessage execute(Update update) {
        if (!update.getMessage().getText().equals("Посмотреть заявки")){
            return null;
        }
        UserModel userModel = userRpository.findByTgId(update
                .getMessage().getFrom().getId().toString());
        userModel.setStateEnum(StateEnum.ACTUAL_ORDERS);
        userRpository.save(userModel);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Не актуально");
        inlineKeyboardButton.setCallbackData("not actual");

        inlineKeyboardMarkup.setKeyboard(Collections.singletonList
                (Collections.singletonList(inlineKeyboardButton)));

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton("Еще"));
        keyboardRow.add(new KeyboardButton("Главная"));
        replyKeyboardMarkup.
                setKeyboard(Collections.singletonList(keyboardRow));
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);


        List<OrderModel> list = orderRepo.findAllByIfAnswered(false);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(list.get(0).getCarName()+"\n");
        stringBuilder.append(list.get(0).getContact()+" "+list.get(0).getName());

        sendMessage.setText(stringBuilder.toString());
        return sendMessage;
    }
}
