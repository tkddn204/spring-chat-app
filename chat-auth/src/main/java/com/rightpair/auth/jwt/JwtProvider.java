package com.rightpair.auth.jwt;

import com.rightpair.auth.exception.JwtVerifyException;
import com.rightpair.auth.jwt.dto.JwtPayload;
import com.rightpair.auth.service.response.OAuthIdTokenPayload;
import com.rightpair.core.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtProvider {
    private static final String JWT_ISSUER = "chat-app";
    private static final String CLIENT_ID_KEY = "client_id";
    private static final String CLIENT_EMAIL_KEY = "client_email";

    private final SecretKey secretKey;

    public JwtProvider(@Value("${service.jwt.secret-key}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String createToken(JwtPayload jwtPayload, long expiration) {
        return Jwts.builder()
                .claim(CLIENT_ID_KEY, jwtPayload.id())
                .claim(CLIENT_EMAIL_KEY, jwtPayload.email())
                .issuer(JWT_ISSUER)
                .issuedAt(jwtPayload.issuedAt())
                .expiration(new Date(jwtPayload.issuedAt().getTime() + expiration))
                .signWith(secretKey)
                .compact();
    }

    public JwtPayload verifyToken(String jwtToken, boolean isStrict) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jwtToken)
                    .getPayload();
            return JwtPayload.fromClaims(
                    claims.get(CLIENT_ID_KEY, String.class),
                    claims.get(CLIENT_EMAIL_KEY, String.class),
                    claims.getIssuedAt());
        } catch (ExpiredJwtException e) {
            if (!isStrict) {
                return JwtPayload.fromClaims(
                        e.getClaims().get(CLIENT_ID_KEY, String.class),
                        e.getClaims().get(CLIENT_EMAIL_KEY, String.class),
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

    public OAuthIdTokenPayload verifyIdTokenPayload(String idToken, List<PublicKey> publicKeys) {
        try {
            for (PublicKey publicKey : publicKeys) {
                try {
                    Claims claims = Jwts.parser()
                            .verifyWith(publicKey)
                            .build()
                            .parseSignedClaims(idToken)
                            .getPayload();
                    return OAuthIdTokenPayload.create(claims);
                } catch (JwtException ignored) {
                }
            }
            throw new JwtVerifyException(ErrorCode.JWT_PUBLIC_KEY_NOT_VALID_ERROR);
        } catch (ExpiredJwtException e) {
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