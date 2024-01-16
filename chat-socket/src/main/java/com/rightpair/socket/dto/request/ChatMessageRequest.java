package com.rightpair.socket.dto.request;

import com.rightpair.socket.entity.ChatMember;
import com.rightpair.socket.entity.type.MessageType;
import com.rightpair.socket.entity.type.RoomType;

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
