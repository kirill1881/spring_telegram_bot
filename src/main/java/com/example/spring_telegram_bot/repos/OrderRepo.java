package com.example.spring_telegram_bot.repos;

import com.example.spring_telegram_bot.models.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<OrderModel, Long> {
    List<OrderModel> findAllByIfAnswered(boolean ifAnswered);
}
