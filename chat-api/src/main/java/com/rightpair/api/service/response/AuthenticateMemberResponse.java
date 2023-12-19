package com.rightpair.api.service.response;

import com.rightpair.api.jwt.JwtPair;

public record AuthenticateMemberResponse(
        String accessToken,
        String refreshToken
) {
    public static AuthenticateMemberResponse fromJwtPair(JwtPair jwtPair) {
        return new AuthenticateMemberResponse(jwtPair.accessToken(), jwtPair.refreshToken());
    }
}
