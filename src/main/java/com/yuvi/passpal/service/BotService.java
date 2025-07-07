package com.yuvi.passpal.service;

import com.yuvi.passpal.model.Password;
import com.yuvi.passpal.repository.BotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BotService {
    private final BotRepo botRepo;

    @Autowired
    public BotService(BotRepo botRepo){
        this.botRepo = botRepo;
    }

    public String getPassword(String name){
        return botRepo.findByName(name).map(Password::getPassword).orElse("No password found for " + name);
    }
}
