package com.rightpair.socket.dto.request;

import com.rightpair.socket.entity.type.MessageType;

public record ChatMessageControllerRequest(
        MessageType messageType,
        String roomId,
        String message,
        String memberId,
        String memberName,
        Long timestamp
) {
}
