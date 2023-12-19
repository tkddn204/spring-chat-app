package com.rightpair.api.jwt.dto;

import java.time.Instant;
import java.util.Date;

public record JwtPayload (
        String id,
        String email,
        String name,
        Date issuedAt
)
{
    public static JwtPayload from(String id, String email, String name) {
        return new JwtPayload(id, email, name, Date.from(Instant.now()));
    }

    public static JwtPayload fromClaims(String id, String email, String name, Date issuedAt) {
        return new JwtPayload(id, email, name, issuedAt);
    }

}
