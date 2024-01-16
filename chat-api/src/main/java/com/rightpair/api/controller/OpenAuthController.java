package com.rightpair.api.controller;

import com.rightpair.api.dto.request.OpenAuthControllerRequest;
import com.rightpair.api.dto.response.AuthenticateMemberResponse;
import com.rightpair.api.jwt.JwtPair;
import com.rightpair.api.oauth.service.GoogleOAuthService;
import com.rightpair.api.oauth.service.KakaoOAuthService;
import com.rightpair.core.exception.ErrorCode;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1/oauth")
@RequiredArgsConstructor
public class OpenAuthController {

    private final GoogleOAuthService googleOAuthService;
    private final KakaoOAuthService kakaoOAuthService;

    @PostMapping("/login/{provider}")
    public ResponseEntity<AuthenticateMemberResponse> requestOpenAuth(
            @PathVariable("provider") @NotBlank String provider,
            @RequestBody OpenAuthControllerRequest request
    ) throws IOException {
        JwtPair jwtPair = switch (provider) {
            case "google" -> googleOAuthService.provideMemberJwtByOAuthCode(request.code());
            case "kakao" -> kakaoOAuthService.provideMemberJwtByOAuthCode(request.code());
            default -> throw new BadRequestException(ErrorCode.INVALID_VALIDATION.getMessage());
        };
        return ResponseEntity.ok(AuthenticateMemberResponse.fromJwtPair(jwtPair));
    }
}
