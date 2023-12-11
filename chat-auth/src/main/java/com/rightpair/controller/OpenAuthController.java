package com.rightpair.controller;

import com.rightpair.controller.request.OpenAuthControllerRequest;
import com.rightpair.dto.AuthenticateMemberResponse;
import com.rightpair.oauth.service.GoogleOAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1/oauth")
@RequiredArgsConstructor
public class OpenAuthController {

    private final GoogleOAuthService googleOAuthService;

    @PostMapping("/login/{provider}")
    public ResponseEntity<AuthenticateMemberResponse> requestOpenAuth(
            @PathVariable("provider") String provider,
            @RequestBody OpenAuthControllerRequest request
    ) throws IOException {
        return ResponseEntity.ok(
                AuthenticateMemberResponse.fromJwtPair(
                        googleOAuthService.provideMemberJwtByOAuthCode(provider, request.code())));
    }
}
