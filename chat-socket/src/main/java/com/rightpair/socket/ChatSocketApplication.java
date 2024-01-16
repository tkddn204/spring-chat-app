package com.rightpair.socket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.rightpair")
public class ChatSocketApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatSocketApplication.class, args);
    }
}
