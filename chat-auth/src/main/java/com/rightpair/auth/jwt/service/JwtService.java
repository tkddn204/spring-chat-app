package com.rightpair.auth.jwt.service;

import com.rightpair.auth.dto.OAuthIdTokenPayload;
import com.rightpair.auth.jwt.JwtPair;
import com.rightpair.auth.jwt.JwtProvider;
import com.rightpair.auth.jwt.dto.JwtPayload;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final JwtProvider jwtProvider;

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;

    @Getter
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

    public OAuthIdTokenPayload decodeOauthIdToken(String idToken, List<PublicKey> oauthKeys) {
        return jwtProvider.verifyIdTokenPayload(idToken, oauthKeys);
    }
}
