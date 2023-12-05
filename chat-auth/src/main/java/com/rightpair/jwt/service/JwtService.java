package com.rightpair.jwt.service;

import com.rightpair.jwt.JwtPair;
import com.rightpair.jwt.JwtProvider;
import com.rightpair.jwt.dto.JwtPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final JwtProvider jwtProvider;

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;

    @Value("${service.jwt.refresh-expiration}")
    private Long refreshExpiration;

    public JwtPair createTokenPair(JwtPayload jwtPayload) {
        String accessToken = jwtProvider.createToken(jwtPayload, accessExpiration);
        String refreshToken = jwtProvider.createToken(jwtPayload, refreshExpiration);
        return JwtPair.create(accessToken, refreshToken);
    }

    public JwtPair refreshAccessToken(String refreshToken, JwtPayload jwtPayload) {
        String accessToken = jwtProvider.createToken(jwtPayload, accessExpiration);
        return JwtPair.create(accessToken, refreshToken);
    }

    public JwtPayload verifyAccessToken(String accessToken) {
        return jwtProvider.verifyToken(accessToken, false);
    }

    public JwtPayload verifyRefreshToken(String refreshToken) {
        return jwtProvider.verifyToken(refreshToken, true);
    }
}