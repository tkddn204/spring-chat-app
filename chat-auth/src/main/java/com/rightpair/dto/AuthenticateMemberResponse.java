package com.rightpair.dto;

import com.rightpair.jwt.JwtPair;

public record AuthenticateMemberResponse(
        String accessToken,
        String refreshToken
) {
    public static AuthenticateMemberResponse fromJwtPair(JwtPair jwtPair) {
        return new AuthenticateMemberResponse(jwtPair.accessToken(), jwtPair.refreshToken());
    }
}
