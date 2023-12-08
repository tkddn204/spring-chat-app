package com.rightpair.pubsub;

import com.rightpair.domain.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import static com.rightpair.config.RedisMessageConfig.CHAT_CHANNEL_TOPIC;

@RequiredArgsConstructor
@Component
public class ChatRoomPublisher {

    private final StringRedisTemplate redisTemplate;

    public void publish(String roomId, ChatMessage message) {
        redisTemplate.convertAndSend(CHAT_CHANNEL_TOPIC, message);
    }
}
