package com.rightpair.jwt;

import com.rightpair.exception.ErrorCode;
import com.rightpair.exception.JwtVerifyException;
import com.rightpair.jwt.dto.JwtPayload;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {
    private static final String USER_ID_KEY = "client_id";
    private static final String USER_EMAIL_KEY = "client_email";

    private final String issuer;

    private final SecretKey secretKey;

    public JwtProvider(
            @Value("${spring.application.name}") String issuer,
            @Value("${service.jwt.secret-key}") String secretKey
    ) {
        this.issuer = issuer;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String createToken(JwtPayload jwtPayload, long expiration) {
        return Jwts.builder()
                .claim(USER_ID_KEY, jwtPayload.id())
                .claim(USER_EMAIL_KEY, jwtPayload.email())
                .issuer(issuer)
                .issuedAt(jwtPayload.issuedAt())
                .expiration(new Date(jwtPayload.issuedAt().getTime() + expiration))
                .signWith(secretKey)
                .compact();
    }

    private JwtPayload verifyToken(String jwtToken, boolean isStrict) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jwtToken)
                    .getPayload();
            return JwtPayload.fromClaims(
                    claims.get(USER_ID_KEY, String.class),
                    claims.get(USER_EMAIL_KEY, String.class),
                    claims.getIssuedAt());
        } catch (ExpiredJwtException e) {
            if (!isStrict) {
                return JwtPayload.fromClaims(
                        e.getClaims().get(USER_ID_KEY, String.class),
                        e.getClaims().get(USER_EMAIL_KEY, String.class),
                        e.getClaims().getIssuedAt());
            }
            throw new JwtVerifyException(ErrorCode.JWT_EXPIRED_ERROR);
        } catch (SignatureException e) {
            throw new JwtVerifyException(ErrorCode.JWT_INVALID_SIGNATURE_ERROR);
        } catch (MalformedJwtException e) {
            throw new JwtVerifyException(ErrorCode.JWT_MALFORMED_ERROR);
        } catch (UnsupportedJwtException | IllegalArgumentException e) {
            throw new JwtVerifyException(ErrorCode.JWT_UNSUPPORTED_ERROR);
        }
    }
}