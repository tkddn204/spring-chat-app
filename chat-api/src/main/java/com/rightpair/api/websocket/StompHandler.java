package com.rightpair.api.websocket;

//import com.rightpair.api.jwt.service.JwtService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import org.springframework.messaging.support.ChannelInterceptor;

//@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
//    private final JwtService jwtService;
//
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        switch (accessor.getCommand()) {
//            case CONNECT -> {
//                String jwtToken = accessor.getFirstNativeHeader("authorize");
//                jwtService.verifyAccessToken(jwtToken);
//            }
//            case SUBSCRIBE -> {
//                message.getPayload();
//            }
//            default -> {
//
//            }
//        }
//        return message;
//    }
}
