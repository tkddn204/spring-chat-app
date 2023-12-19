package com.rightpair.api.repository.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rightpair.api.domain.chat.ChatMessage;
import com.rightpair.api.domain.chat.ChatRoom;
import com.rightpair.api.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ChatRoomRedisRepository implements RedisRepository<ChatRoom> {

    public final static String CHAT_CHANNEL_TOPIC = "pubsub:chat";
    private final static String KEY_PREFIX = "chat:room:";
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void save(ChatRoom entity) {
        String key = KEY_PREFIX + entity.roomId();
        String value = valueAsString(entity);
        redisTemplate.opsForValue().set(key, value);
    }

    private String valueAsString(ChatRoom entity) {
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private ChatRoom readValue(String value) {
        try {
            return objectMapper.readValue(value, ChatRoom.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<ChatRoom> findByKey(String key) {
        return Optional.ofNullable(readValue(redisTemplate.opsForValue().get(KEY_PREFIX + key)));
    }

    public Boolean existsKey(String key) {
        return redisTemplate.hasKey(KEY_PREFIX + key);
    }

    public List<ChatRoom> findAllChatList() {
        return Objects.requireNonNull(redisTemplate.keys(KEY_PREFIX + "*"))
                .stream().map(key -> readValue(redisTemplate.opsForValue().get(key))).toList();
    }

    @Override
    public Boolean deleteByKey(String key) {
        return redisTemplate.delete(KEY_PREFIX + key);
    }

    public void publish(ChatMessage message) {
        try {
            redisTemplate.convertAndSend(CHAT_CHANNEL_TOPIC + message.getRoomId(), objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
