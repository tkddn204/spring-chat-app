package com.rightpair.api.controller.request;

import com.rightpair.api.type.MessageType;

public record ChatMessageControllerRequest(
        MessageType messageType,
        String roomId,
        String message,
        Long timestamp
) {
}
