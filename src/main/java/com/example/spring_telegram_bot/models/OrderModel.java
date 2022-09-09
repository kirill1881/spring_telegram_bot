package com.example.spring_telegram_bot.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "order_model")
@Data
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "first_name")
    String name;

    @Column(name = "contact")
    String contact;

    @Column(name = "car_name")
    String carName;

    @Column(name = "if_answered")
    boolean ifAnswered;
}
