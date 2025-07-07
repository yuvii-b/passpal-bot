package com.yuvi.passpal.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class PassPalBot extends TelegramLongPollingBot {

    private final BotConfig config;

    @Autowired
    public PassPalBot(BotConfig config){
        this.config = config;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String msg = update.getMessage().getText();
        String chatId = update.getMessage().getChatId().toString();

        if(!chatId.equals(config.getChatId())){
            try{
                execute(new SendMessage(chatId, "You are not authorized to use this bot!"));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        SendMessage message = new SendMessage(chatId, "Hello " + msg);

        try{
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }
}
