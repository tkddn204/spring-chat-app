package com.rightpair.socket.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        switch (Objects.requireNonNull(accessor.getCommand())) {
//            case StompCommand.CONNECT, StompCommand.SEND, StompCommand.DISCONNECT -> {
//                log.debug(String.format("message : %s", message.getPayload()));
//                String jwtToken = accessor.getFirstNativeHeader("Authorization");
//                JwtPayload jwtPayload = jwtService.verifyAccessToken(jwtToken);
//                return MessageBuilder.fromMessage(message)
//                        .setHeader("member", jwtPayload)
//                        .build();
//            }
//            default -> {
//
//            }
//        }
        return message;
    }
}
