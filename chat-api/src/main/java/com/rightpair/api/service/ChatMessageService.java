package com.rightpair.api.service;

import com.rightpair.api.domain.chat.ChatMessage;
import com.rightpair.api.domain.chat.ChatRoom;
import com.rightpair.api.dto.ChatMessageRequest;
import com.rightpair.api.exception.ChatRoomNotFoundException;
import com.rightpair.api.pubsub.ChatRoomPublisher;
import com.rightpair.api.repository.chat.ChatRoomRedisRepository;
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
