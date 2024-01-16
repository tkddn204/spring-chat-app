package com.rightpair.socket.entity;


import com.rightpair.socket.entity.type.MessageType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ChatDirectMessage {
    private final String messageId;
    private final MessageType messageType;
    private final String roomId;
    private final ChatMember chatMember;
    private final String message;
    private final Long timestamp;

    private ChatDirectMessage(String messageId, MessageType messageType, String roomId, ChatMember chatMember, String message, Long timestamp) {
        this.messageId = messageId;
        this.messageType = messageType;
        this.roomId = roomId;
        this.chatMember = chatMember;
        this.message = message;
        this.timestamp = timestamp;
    }

    public static ChatDirectMessage create(MessageType messageType, String roomId, ChatMember chatMember, String message, Long timestamp) {
        String randomMessageId = UUID.randomUUID().toString();
        String exchangeMessage = switch (messageType) {
            case ENTER -> chatMember.getName() + "님이 입장하셨습니다.";
            case EXIT -> chatMember.getName() + "님이 퇴장하셨습니다.";
            case MESSAGE -> message;
        };
        return new ChatDirectMessage(randomMessageId, messageType, roomId, chatMember, exchangeMessage, timestamp);
    }
}
