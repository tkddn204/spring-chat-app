package com.rightpair.api.jwt.dto;

import java.time.Instant;
import java.util.Date;

public record JwtPayload (
        String id,
        String email,
        Date issuedAt
)
{
    public static JwtPayload from(String id, String email) {
        return new JwtPayload(id, email, Date.from(Instant.now()));
    }

    public static JwtPayload fromClaims(String id, String email, Date issuedAt) {
        return new JwtPayload(id, email, issuedAt);
    }

}
