package com.rightpair.api.controller;

import com.rightpair.api.controller.request.ChatMessageControllerRequest;
import com.rightpair.api.dto.ChatMessageRequest;
import com.rightpair.api.service.ChatMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/message")
    public void message(@Valid ChatMessageControllerRequest request) {
        chatMessageService.createChatMessage(ChatMessageRequest.from(request));
    }
}
