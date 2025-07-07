package com.yuvi.passpal.bot;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfig {
    private final Dotenv dotenv = Dotenv.load();

    public String getBotUsername(){
        return dotenv.get("TELEGRAM_BOT_USERNAME");
    }

    public String getBotToken(){
        return dotenv.get("TELEGRAM_BOT_TOKEN");
    }

    public String getChatId(){
        return dotenv.get("CHAT_ID");
    }

}
