package com.example.spring_telegram_bot;

import com.example.spring_telegram_bot.models.UserModel;
import com.example.spring_telegram_bot.repos.UserRpository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class Helper {
    @Autowired
    UserRpository userRpository;

    private static Helper helper = null;

    public Helper() {
        helper = this;
    }

    public static void saveUser(UserModel userModel){
        helper.userRpository.save(userModel);
    }
}
