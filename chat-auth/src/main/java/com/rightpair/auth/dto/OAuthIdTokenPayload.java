package com.rightpair.auth.dto;

import io.jsonwebtoken.Claims;

import java.util.Date;

public record OAuthIdTokenPayload(
        String iss,
        String aud,
        String sub,
        String email,
        boolean emailVerified,
        String name,
        String picture,
        String locale,
        Date iat,
        Date exp) {

    public static OAuthIdTokenPayload create(Claims claims) {
        return new OAuthIdTokenPayload(
                claims.getIssuer(),
                claims.getAudience().stream().findFirst().orElse(null),
                claims.getSubject(),
                claims.get("email", String.class),
                claims.get("email_verified", Boolean.class),
                claims.get("name", String.class),
                claims.get("picture", String.class),
                claims.get("locale", String.class),
                claims.getIssuedAt(),
                claims.getExpiration()
        );
    }
}
