package com.rightpair.socket.dto.request;

public record CreateChatRoomControllerRequest(
        String roomName,
        String memberId,
        String memberName
) {
}
