package com.rightpair.domain.chat;


import com.rightpair.type.MessageType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ChatMessage {
    private final String messageId;
    private final MessageType messageType;
    private final String roomId;
    private final ChatMember chatMember;
    private final String message;
    private final Long timestamp;

    private ChatMessage(String messageId, MessageType messageType, String roomId, ChatMember chatMember, String message, Long timestamp) {
        this.messageId = messageId;
        this.messageType = messageType;
        this.roomId = roomId;
        this.chatMember = chatMember;
        this.message = message;
        this.timestamp = timestamp;
    }

    public static ChatMessage create(MessageType messageType, String roomId, ChatMember chatMember, String message, Long timestamp) {
        String randomMessageId = UUID.randomUUID().toString();
        String exchangeMessage = switch (messageType) {
            case ENTER -> chatMember.getName() + "님이 입장하셨습니다.";
            case CONNECTED -> message;
            case EXIT -> chatMember.getName() + "님이 퇴장하셨습니다.";
        };
        return new ChatMessage(randomMessageId, messageType, roomId, chatMember, exchangeMessage, timestamp);
    }
}
