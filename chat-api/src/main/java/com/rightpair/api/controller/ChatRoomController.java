package com.rightpair.api.controller;

import com.rightpair.api.controller.request.CreateChatRoomControllerRequest;
import com.rightpair.api.dto.ChatRoomListResponse;
import com.rightpair.api.dto.ChatRoomResponse;
import com.rightpair.api.service.ChatRoomService;
import com.rightpair.auth.resolver.AuthContext;
import com.rightpair.auth.security.JwtPrincipal;
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
