package com.rightpair.api.dto.response;

import com.rightpair.api.jwt.JwtPair;

public record RegisterMemberResponse(
        String accessToken,
        String refreshToken
) {
    public static RegisterMemberResponse fromJwtPair(JwtPair jwtPair) {
        return new RegisterMemberResponse(jwtPair.accessToken(), jwtPair.refreshToken());
    }
}
