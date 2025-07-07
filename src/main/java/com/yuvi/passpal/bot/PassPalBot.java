package com.yuvi.passpal.bot;

import com.yuvi.passpal.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class PassPalBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final BotService botService;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    public PassPalBot(BotConfig config, BotService botService){
        this.config = config;
        this.botService = botService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
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

            String response;
            if(msg.equalsIgnoreCase("/show")){
                response = botService.getAllPasswords();
            } else if(msg.startsWith("/pass")){
                String[] parts = msg.split(" ", 2);
                if(parts.length == 2){
                    response = botService.getPassword(parts[1]);
                } else{
                    response = "Usage: /pass <name for which you want the password>";
                }
            } else if(msg.startsWith("/add")){
                String[] parts = msg.split(" ", 3);
                if(parts.length == 3){
                    response = botService.addPassword(parts[1], parts[2]);
                } else{
                    response = "Usage: /add <name> <password>";
                }
            } else{
                response = "Unknown command. Try /pass <name> or /show";
            }

            SendMessage message = new SendMessage(chatId, response);

            try{
                Message sentMsg = execute(message);
                scheduleDelete(chatId, sentMsg.getMessageId());
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
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

    private void scheduleDelete(String chatId, int msgId){
        scheduler.schedule(() -> {
            try {
                execute(new DeleteMessage(chatId, msgId));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }, 2, TimeUnit.MINUTES);
    }
}
