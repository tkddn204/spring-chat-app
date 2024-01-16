package com.rightpair.socket.service;

import com.rightpair.socket.dto.response.ChatRoomListResponse;
import com.rightpair.socket.entity.ChatMember;
import com.rightpair.socket.entity.ChatMessage;
import com.rightpair.socket.entity.ChatRoom;
import com.rightpair.socket.exception.ChatRoomAlreadyExistedException;
import com.rightpair.socket.exception.ChatRoomNotFoundException;
import com.rightpair.socket.exception.MemberNotEnteredChatRoomException;
import com.rightpair.socket.pubsub.ChatRoomSubscriber;
import com.rightpair.socket.repository.chat.ChatRoomRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRedisRepository chatRoomRedisRepository;
    private final RedisMessageListenerContainer redisMessageListener;
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
//        if (chatRooms.isEmpty()) {
//            throw new ChatRoomNotFoundException();
//        }
        return ChatRoomListResponse.create(chatRooms, chatRooms.size());
    }

    public void enterChatRoom(String memberId, String memberName, String chatRoomId) {
        ChatMember chatMember = ChatMember.create(memberId, memberName);
        ChatRoom chatRoom = chatRoomRedisRepository.findByKey(chatRoomId)
                .orElseThrow(ChatRoomNotFoundException::new);

        Optional<ChatMember> enteredMember = chatRoom.members().stream()
                .filter(member -> member.getId().equals(memberId))
                .findFirst();
        if (enteredMember.isEmpty()) {
            chatRoom.members().add(chatMember);
            chatRoomRedisRepository.save(chatRoom);
        }
        redisMessageListener.addMessageListener(chatRoomSubscriber,
                ChannelTopic.of(ChatRoomRedisRepository.CHAT_CHANNEL_TOPIC + chatRoomId));
    }

    public List<ChatMessage> getChatMessages(String memberId, String chatRoomId) {
        ChatRoom chatRoom = chatRoomRedisRepository.findByKey(chatRoomId)
                .orElseThrow(ChatRoomNotFoundException::new);

        chatRoom.members().stream()
                .filter(chatMember -> chatMember.getId().equals(memberId))
                .findFirst().orElseThrow(MemberNotEnteredChatRoomException::new);

        return chatRoom.messages();
    }
}
