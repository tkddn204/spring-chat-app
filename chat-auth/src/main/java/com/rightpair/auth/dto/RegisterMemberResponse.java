package com.rightpair.auth.dto;

import com.rightpair.auth.jwt.JwtPair;

public record RegisterMemberResponse(
        String accessToken,
        String refreshToken
) {
    public static RegisterMemberResponse fromJwtPair(JwtPair jwtPair) {
        return new RegisterMemberResponse(jwtPair.accessToken(), jwtPair.refreshToken());
    }
}
