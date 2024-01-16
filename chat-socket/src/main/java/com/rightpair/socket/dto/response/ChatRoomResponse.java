package com.rightpair.socket.dto.response;

import com.rightpair.socket.entity.ChatRoom;

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
