package com.rightpair.api.jwt;

public record JwtPair (
        String accessToken,
        String refreshToken
) {
    public static JwtPair create(String accessToken, String refreshToken) {
        return new JwtPair(accessToken, refreshToken);
    }
}
