package com.rightpair.api.dto;

import com.rightpair.api.domain.chat.ChatRoom;

public record ChatRoomResponse(
        String roomId,
        String roomName,
        int totalMember
) {
    public static ChatRoomResponse create(ChatRoom chatRoom) {
        return new ChatRoomResponse(
                chatRoom.roomId(),
                chatRoom.roomName(),
                chatRoom.members().size()
        );
    }
}
