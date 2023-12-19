package com.rightpair.api.interceptor;

import com.rightpair.api.jwt.dto.JwtPayload;
import com.rightpair.api.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompChannelInterceptor implements ChannelInterceptor {
    private final JwtService jwtService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        switch (Objects.requireNonNull(accessor.getCommand())) {
            case CONNECT, SEND, DISCONNECT -> {
                log.debug(String.format("message : %s", message.getPayload()));
                String jwtToken = accessor.getFirstNativeHeader("Authorization");
                JwtPayload jwtPayload = jwtService.verifyAccessToken(jwtToken);
                return MessageBuilder.fromMessage(message)
                        .setHeader("member", jwtPayload)
                        .build();
            }
            default -> {

            }
        }
        return message;
    }
}
