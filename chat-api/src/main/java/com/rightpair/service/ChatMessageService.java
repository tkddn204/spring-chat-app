package com.rightpair.service;

import com.rightpair.domain.chat.ChatMessage;
import com.rightpair.domain.chat.ChatRoom;
import com.rightpair.dto.ChatMessageRequest;
import com.rightpair.exception.ChatRoomNotFoundException;
import com.rightpair.pubsub.ChatRoomPublisher;
import com.rightpair.repository.chat.ChatRoomRedisRepository;
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
        chatRoomPublisher.publish(request.roomId(), chatMessage);
    }
}
