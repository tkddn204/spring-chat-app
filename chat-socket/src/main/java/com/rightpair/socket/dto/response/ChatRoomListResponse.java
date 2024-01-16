package com.rightpair.socket.dto.response;

import com.rightpair.socket.entity.ChatRoom;

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
