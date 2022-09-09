package com.example.spring_telegram_bot.repos;

import com.example.spring_telegram_bot.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRpository extends JpaRepository<UserModel, Long> {
}
