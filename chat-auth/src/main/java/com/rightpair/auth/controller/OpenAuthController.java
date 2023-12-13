package com.rightpair.auth.controller;

import com.rightpair.auth.controller.request.OpenAuthControllerRequest;
import com.rightpair.auth.oauth.service.GoogleOAuthService;
import com.rightpair.auth.service.response.AuthenticateMemberResponse;
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
