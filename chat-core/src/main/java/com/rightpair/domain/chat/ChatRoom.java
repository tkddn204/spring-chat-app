package com.rightpair.domain.chat;

import java.util.Collections;
import java.util.List;
import java.util.UUID;


public record ChatRoom(
        String roomId,
        String roomName,
        ChatMember leader,
        List<ChatMember> members,
        List<ChatMessage> messages,
        Long createdAt
) {
    public static ChatRoom create(String roomName, ChatMember leader) {
        String randomRoomId = UUID.randomUUID().toString();
        return new ChatRoom(randomRoomId, roomName, leader,
                Collections.emptyList(), Collections.emptyList(), System.currentTimeMillis());
    }
}
