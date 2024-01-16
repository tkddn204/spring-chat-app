package com.rightpair.socket.controller;

import com.rightpair.socket.dto.request.CreateChatRoomControllerRequest;
import com.rightpair.socket.dto.response.ChatRoomListResponse;
import com.rightpair.socket.dto.response.ChatRoomResponse;
import com.rightpair.socket.entity.ChatMessage;
import com.rightpair.socket.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/chat")
@RestController
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping
    public ResponseEntity<ChatRoomListResponse> getAllChatRooms() {
        return ResponseEntity.ok(chatRoomService.findAllChatRoom());
    }

    @GetMapping("/room/{chat_room_id}")
    @ResponseBody
    public ResponseEntity<ChatRoomResponse> roomInfo(@PathVariable("chat_room_id") String chatRoomId) {
        return ResponseEntity.ok(
                ChatRoomResponse.create(chatRoomService.findByRoomId(chatRoomId)));
    }

    @PostMapping("/room")
    public ResponseEntity<ChatRoomResponse> createRoom(
            @RequestBody CreateChatRoomControllerRequest request
    ) {
        return ResponseEntity.ok(
                ChatRoomResponse.create(
                        chatRoomService.createChatRoom(
                                request.memberId(), request.memberName(), request.roomName())));
    }

    @GetMapping("/room/enter/{chat_room_id}")
    public ResponseEntity<Void> enterChatRoom(
            @PathVariable("chat_room_id") String chatRoomId
    ) {
//        chatRoomService.enterChatRoom(jwtPrincipal.getMemberId(), jwtPrincipal.getMemberName(), chatRoomId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/room/message/{chat_room_id}")
    public ResponseEntity<List<ChatMessage>> getChatMessages(
            @PathVariable("chat_room_id") String chatRoomId
    ) {
//        return ResponseEntity.ok().body(chatRoomService.getChatMessages(jwtPrincipal.getMemberId(), chatRoomId));
        return ResponseEntity.ok().build();
    }
}
