package com.rightpair.api.service;

import com.rightpair.api.domain.chat.ChatMember;
import com.rightpair.api.domain.chat.ChatRoom;
import com.rightpair.api.dto.ChatRoomListResponse;
import com.rightpair.api.exception.ChatRoomAlreadyExistedException;
import com.rightpair.api.exception.ChatRoomNotFoundException;
import com.rightpair.api.pubsub.ChatRoomSubscriber;
import com.rightpair.api.repository.chat.ChatRoomRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRedisRepository chatRoomRedisRepository;
    private final ChatRoomSubscriber chatRoomSubscriber;

    public ChatRoom createChatRoom(String leaderMemberId, String leaderMemberName, String roomName) {
        ChatMember leaderChatMember = ChatMember.create(leaderMemberId, leaderMemberName);
        if (chatRoomRedisRepository.existsKey(roomName)) {
            throw new ChatRoomAlreadyExistedException();
        }

        ChatRoom newChatRoom = ChatRoom.create(roomName, leaderChatMember);
        chatRoomRedisRepository.save(newChatRoom);

        return newChatRoom;
    }

    public ChatRoom findByRoomId(String roomId) {
        return chatRoomRedisRepository.findByKey(roomId)
                .orElseThrow(ChatRoomNotFoundException::new);
    }

    public ChatRoomListResponse findAllChatRoom() {
        List<ChatRoom> chatRooms = chatRoomRedisRepository.findAllChatList();
        if (chatRooms.isEmpty()) {
            throw new ChatRoomNotFoundException();
        }
        return ChatRoomListResponse.create(chatRooms, chatRooms.size());
    }

    public void enterChatRoom(String memberId, String memberName, String chatRoomId) {
        ChatMember chatMember = ChatMember.create(memberId, memberName);
        ChatRoom chatRoom = chatRoomRedisRepository.findByKey(chatRoomId)
                .orElseThrow(ChatRoomNotFoundException::new);

        chatRoom.members().add(chatMember);
        chatRoomRedisRepository.save(chatRoom);
//        chatRoomRedisRepository.addChatMessageListener(chatRoomSubscriber);
    }
}
