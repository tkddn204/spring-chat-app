package com.rightpair.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.rightpair")
public class ChatAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatAuthApplication.class, args);
    }
}
