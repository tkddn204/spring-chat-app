package com.rightpair.api.dto;

import com.rightpair.api.controller.request.ChatMessageControllerRequest;
import com.rightpair.api.domain.chat.ChatMember;
import com.rightpair.api.type.MessageType;
import com.rightpair.api.type.RoomType;

public record ChatMessageRequest(
        RoomType roomType,
        MessageType messageType,
        String roomId,
        ChatMember chatMember,
        String message,
        Long timestamp
) {
    public static ChatMessageRequest from(RoomType roomType, String memberId, String memberName, ChatMessageControllerRequest request) {
        return new ChatMessageRequest(
                roomType,
                request.messageType(),
                request.roomId(),
                ChatMember.create(memberId, memberName),
                request.message(),
                request.timestamp()
        );
    }
}
