package com.rightpair.socket.pubsub;

import com.rightpair.socket.entity.ChatMessage;
import com.rightpair.socket.repository.chat.ChatRoomRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChatRoomPublisher {

    private final ChatRoomRedisRepository chatRoomRedisRepository;

    public void publish(ChatMessage message) {
        chatRoomRedisRepository.publish(message);
    }
}
