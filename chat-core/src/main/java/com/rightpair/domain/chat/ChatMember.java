package com.rightpair.domain.chat;

import lombok.Getter;

@Getter
public class ChatMember {
    private final String id;
    private final String name;

    private ChatMember(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ChatMember create(String id, String name) {
        return new ChatMember(id, name);
    }
}
