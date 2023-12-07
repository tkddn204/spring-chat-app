package com.rightpair.dto;

import com.rightpair.domain.chat.ChatRoom;

import java.util.List;

public record ChatRoomListResponse(
        List<ChatRoom> chatRooms,
        Integer chatRoomCount
) {

    public static ChatRoomListResponse create(List<ChatRoom> chatRooms, Integer chatRoomCount) {
        return new ChatRoomListResponse(chatRooms, chatRoomCount);
    }
}
