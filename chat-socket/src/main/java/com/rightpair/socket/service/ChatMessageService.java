package com.rightpair.socket.service;

import com.rightpair.socket.dto.request.ChatMessageRequest;
import com.rightpair.socket.entity.ChatMessage;
import com.rightpair.socket.entity.ChatRoom;
import com.rightpair.socket.exception.ChatRoomNotFoundException;
import com.rightpair.socket.pubsub.ChatRoomPublisher;
import com.rightpair.socket.repository.chat.ChatRoomRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatMessageService {

    private final ChatRoomRedisRepository chatRoomRedisRepository;
    private final ChatRoomPublisher chatRoomPublisher;

    public void createChatMessage(ChatMessageRequest request) {
        ChatMessage chatMessage = ChatMessage.create(
                request.messageType(),
                request.roomId(),
                request.chatMember(),
                request.message(),
                request.timestamp()
        );
        ChatRoom chatRoom = chatRoomRedisRepository.findByKey(request.roomId())
                .orElseThrow(ChatRoomNotFoundException::new);

        chatRoom.messages().add(chatMessage);
        chatRoomRedisRepository.save(chatRoom);
        chatRoomPublisher.publish(chatMessage);
    }
}
