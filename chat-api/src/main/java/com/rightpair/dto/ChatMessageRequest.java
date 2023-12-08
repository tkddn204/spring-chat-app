package com.rightpair.dto;

import com.rightpair.controller.request.ChatMessageControllerRequest;
import com.rightpair.domain.chat.ChatMember;
import com.rightpair.type.MessageType;

public record ChatMessageRequest(
        MessageType messageType,
        String roomId,
        ChatMember chatMember,
        String message,
        Long timestamp
) {
    public static ChatMessageRequest from(ChatMessageControllerRequest request) {
        return new ChatMessageRequest(
                request.messageType(),
                request.roomId(),
                request.chatMember(),
                request.message(),
                request.timestamp()
        );
    }
}
