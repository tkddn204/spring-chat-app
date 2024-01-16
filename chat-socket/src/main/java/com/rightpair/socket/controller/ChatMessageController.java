package com.rightpair.socket.controller;

import com.rightpair.socket.dto.request.ChatMessageControllerRequest;
import com.rightpair.socket.dto.request.ChatMessageRequest;
import com.rightpair.socket.entity.type.RoomType;
import com.rightpair.socket.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/message")
    public void message(@Payload ChatMessageControllerRequest request) {
        chatMessageService.createChatMessage(
                ChatMessageRequest.from(RoomType.DIRECT, request.memberId(), request.memberName(), request));
    }
}
