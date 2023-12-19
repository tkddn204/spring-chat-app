package com.rightpair.api.pubsub;

import com.rightpair.api.domain.chat.ChatMessage;
import com.rightpair.api.repository.chat.ChatRoomRedisRepository;
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
