package com.rightpair.api.controller.request;

import com.rightpair.api.domain.chat.ChatMember;
import com.rightpair.api.type.MessageType;

public record ChatMessageControllerRequest(
        MessageType messageType,
        String roomId,
        ChatMember chatMember,
        String message,
        Long timestamp
) {
}
