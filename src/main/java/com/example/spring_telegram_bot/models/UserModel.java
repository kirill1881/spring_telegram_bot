package com.example.spring_telegram_bot.models;

import com.example.spring_telegram_bot.models.enums.StateEnum;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_model")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "first_name")
    String name;

    @Column(name = "telgram_id")
    String tgId;

    @Enumerated
    @Column(name = "state")
    StateEnum stateEnum;

    @Column(name = "chat_id")
    String chatId;
}
