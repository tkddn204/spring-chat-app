package com.rightpair.dto;

import com.rightpair.domain.chat.ChatRoom;

public record ChatRoomResponse(
        ChatRoom chatRoom
) {
    public static ChatRoomResponse create(ChatRoom chatRoom) {
        return new ChatRoomResponse(chatRoom);
    }
}
