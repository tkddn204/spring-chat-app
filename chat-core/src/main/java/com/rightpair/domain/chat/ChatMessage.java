package com.rightpair.domain.chat;


import com.rightpair.type.MessageType;
import lombok.Getter;

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

    public static ChatMessage create(String messageId, MessageType messageType, String roomId, ChatMember chatMember, String message, Long timestamp) {
        return new ChatMessage(messageId, messageType, roomId, chatMember, message, timestamp);
    }
}
