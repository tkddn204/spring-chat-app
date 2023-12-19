package com.rightpair.api.dto;

import com.rightpair.api.controller.request.ChatMessageControllerRequest;
import com.rightpair.api.domain.chat.ChatMember;
import com.rightpair.api.type.MessageType;

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
