package com.rightpair.controller;

import com.rightpair.controller.request.CreateChatRoomControllerRequest;
import com.rightpair.dto.ChatRoomListResponse;
import com.rightpair.dto.ChatRoomResponse;
import com.rightpair.resolver.AuthContext;
import com.rightpair.security.JwtPrincipal;
import com.rightpair.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestBody CreateChatRoomControllerRequest request,
            @AuthContext JwtPrincipal jwtPrincipal
    ) {
        return ResponseEntity.ok(
                ChatRoomResponse.create(
                        chatRoomService.createChatRoom(
                                jwtPrincipal.getMemberId(), jwtPrincipal.getMemberName(), request.roomName())));
    }

    @GetMapping("/room/enter/{chat_room_id}")
    public ResponseEntity<Void> enterChatRoom(
            @PathVariable("chat_room_id") String chatRoomId,
            @AuthContext JwtPrincipal jwtPrincipal
    ) {
        chatRoomService.enterChatRoom(
                jwtPrincipal.getMemberId(), jwtPrincipal.getMemberName(), chatRoomId);
        return ResponseEntity.ok().build();
    }
}
