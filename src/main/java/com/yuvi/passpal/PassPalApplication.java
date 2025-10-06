package com.yuvi.passpal;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PassPalApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        System.setProperty("SPRING_DATASOURCE_URL",
                System.getenv().getOrDefault("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL")));
        System.setProperty("SPRING_DATASOURCE_USERNAME",
                System.getenv().getOrDefault("SPRING_DATASOURCE_USERNAME", dotenv.get("SPRING_DATASOURCE_USERNAME")));
        System.setProperty("SPRING_DATASOURCE_PASSWORD",
                System.getenv().getOrDefault("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD")));
        System.setProperty("TELEGRAM_BOT_USERNAME",
                System.getenv().getOrDefault("TELEGRAM_BOT_USERNAME", dotenv.get("TELEGRAM_BOT_USERNAME")));
        System.setProperty("TELEGRAM_BOT_TOKEN",
                System.getenv().getOrDefault("TELEGRAM_BOT_TOKEN", dotenv.get("TELEGRAM_BOT_TOKEN")));
        System.setProperty("CHAT_ID",
                System.getenv().getOrDefault("CHAT_ID", dotenv.get("CHAT_ID")));
        System.setProperty("CIPHER_KEY",
                System.getenv().getOrDefault("CIPHER_KEY", dotenv.get("CIPHER_KEY")));

        SpringApplication.run(PassPalApplication.class, args);
    }

}
