package com.rightpair.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.rightpair")
public class ChatApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatApiApplication.class, args);
    }
}
