package com.yuvi.passpal.bot;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotInitializer {

    private final PassPalBot passPalBot;

    @Autowired
    public BotInitializer(PassPalBot passPalBot){
        this.passPalBot = passPalBot;
    }

    @PostConstruct
    public void start(){
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(passPalBot);
            System.out.println("Bot is registered and running!");

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
