package com.yuvi.passpal.service;

import com.yuvi.passpal.model.Password;
import com.yuvi.passpal.repository.BotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BotService {
    private final BotRepo botRepo;
    private final EncryptionService encryptionService;

    @Autowired
    public BotService(BotRepo botRepo, EncryptionService encryptionService){
        this.botRepo = botRepo;
        this.encryptionService = encryptionService;
    }

    public String getPassword(String name) throws Exception{
        List<Password> results = botRepo.findByNameContaining(name);
        if(results.isEmpty()){
            return "No matches found for " + name;
        }
        return results.stream().map(p -> {
            try {
                return p.getName() + " : " + encryptionService.decrypt(p.getPassword());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.joining("\n"));
    }
}
