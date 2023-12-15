package com.rightpair.auth.service.response;

import com.rightpair.auth.jwt.JwtPair;

public record AuthenticateMemberResponse(
        String accessToken,
        String refreshToken
) {
    public static AuthenticateMemberResponse fromJwtPair(JwtPair jwtPair) {
        return new AuthenticateMemberResponse(jwtPair.accessToken(), jwtPair.refreshToken());
    }
}
