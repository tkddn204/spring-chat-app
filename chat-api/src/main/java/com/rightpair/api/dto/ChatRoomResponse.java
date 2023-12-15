package com.rightpair.api.dto;

import com.rightpair.api.domain.chat.ChatRoom;

public record ChatRoomResponse(
        ChatRoom chatRoom
) {
    public static ChatRoomResponse create(ChatRoom chatRoom) {
        return new ChatRoomResponse(chatRoom);
    }
}
