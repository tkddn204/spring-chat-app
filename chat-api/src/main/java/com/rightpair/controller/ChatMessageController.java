package com.rightpair.controller;

import com.rightpair.controller.request.ChatMessageControllerRequest;
import com.rightpair.dto.ChatMessageRequest;
import com.rightpair.service.ChatMessageService;
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
