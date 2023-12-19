package com.rightpair.api.controller;

import com.rightpair.api.controller.request.ChatMessageControllerRequest;
import com.rightpair.api.dto.ChatMessageRequest;
import com.rightpair.api.jwt.dto.JwtPayload;
import com.rightpair.api.service.ChatMessageService;
import com.rightpair.api.type.RoomType;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/message")
    public void message(
            @Header JwtPayload member,
            @Payload ChatMessageControllerRequest request) {
        chatMessageService.createChatMessage(
                ChatMessageRequest.from(RoomType.DIRECT, member.id(), member.name(), request));
    }
}
