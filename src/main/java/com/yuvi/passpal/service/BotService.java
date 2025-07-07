package com.yuvi.passpal.service;

import com.yuvi.passpal.model.Password;
import com.yuvi.passpal.repository.BotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public String getPassword(String name) {
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

    public String getAllPasswords(){
        List<Password> results = botRepo.findAll();
        if(results.isEmpty()){
            return "No passwords found in database";
        }
        return results.stream().map(p -> {
            try {
                return p.getName() + " : " + encryptionService.decrypt(p.getPassword());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.joining("\n"));
    }

    public String addPassword(String name, String password){
        if(name == null || password == null || name.isBlank() || password.isBlank()){
            return "Invalid format, Usage: /add <name> <password>";
        }
        name = name.trim();
        password = password.trim();
        if(botRepo.findByNameIgnoreCase(name).isPresent()){
            return "A password already exists for: " + name;
        }

        Password pass = new Password();
        pass.setName(name);
        try {
            pass.setPassword(encryptionService.encrypt(password));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        botRepo.save(pass);
        return "Password saved for: " + name;
    }

    public String updatePassword(String name, String password) {
        if(name == null || password == null || name.isBlank() || password.isBlank()){
            return "Invalid format, Usage: /update <name> <new password>";
        }
        name = name.trim();
        password = password.trim();
        Optional<Password> checker = botRepo.findByNameIgnoreCase(name);
        if(checker.isEmpty()){
            return "No existing password for: " + name;
        }
        try {
            Password pass = checker.get();
            pass.setPassword(encryptionService.encrypt(password));
            botRepo.save(pass);
            return "Password updated for: " + name;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
