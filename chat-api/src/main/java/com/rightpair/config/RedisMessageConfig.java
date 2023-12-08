package com.rightpair.config;

import com.rightpair.pubsub.ChatRoomSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@RequiredArgsConstructor
@Configuration
public class RedisMessageConfig {

    public final static String CHAT_CHANNEL_TOPIC = "pubsub:chat";

    private final RedisConnectionFactory redisConnectionFactory;
    private final ChatRoomSubscriber chatRoomSubscriber;

    @Bean
    RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(new MessageListenerAdapter(chatRoomSubscriber), chatChannelTopic());
        return container;
    }

    @Bean
    public ChannelTopic chatChannelTopic() {
        return new ChannelTopic(CHAT_CHANNEL_TOPIC);
    }
}
