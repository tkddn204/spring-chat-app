package com.rightpair.controller.request;

import com.rightpair.domain.chat.ChatMember;
import com.rightpair.type.MessageType;

public record ChatMessageControllerRequest(
        MessageType messageType,
        String roomId,
        ChatMember chatMember,
        String message,
        Long timestamp
) {
}
