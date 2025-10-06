package com.yuvi.passpal.bot;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfig {
    @Getter
    @Value("${TELEGRAM_BOT_USERNAME}")
    private String botUsername;

    @Getter
    @Value("${TELEGRAM_BOT_TOKEN}")
    private String botToken;

    @Getter
    @Value("${CHAT_ID}")
    private String chatId;
}
