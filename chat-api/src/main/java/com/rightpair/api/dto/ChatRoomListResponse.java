package com.rightpair.api.dto;

import com.rightpair.api.domain.chat.ChatRoom;

import java.util.List;

public record ChatRoomListResponse(
        List<ChatRoomResponse> chatRooms,
        Integer chatRoomCount
) {

    public static ChatRoomListResponse create(List<ChatRoom> chatRooms, Integer chatRoomCount) {
        return new ChatRoomListResponse(
                chatRooms.stream().map(ChatRoomResponse::create).toList(),
                chatRoomCount
        );
    }
}
