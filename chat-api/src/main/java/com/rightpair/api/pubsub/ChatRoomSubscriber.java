package com.rightpair.api.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rightpair.api.domain.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChatRoomSubscriber implements MessageListener {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String receivedChatMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());
            ChatMessage chatMessage = objectMapper.readValue(receivedChatMessage, ChatMessage.class);
            simpMessageSendingOperations.convertAndSend("/sub/chat/room" + chatMessage.getRoomId(), message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
