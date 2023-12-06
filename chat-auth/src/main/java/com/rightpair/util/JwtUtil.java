package com.rightpair.util;

public class JwtUtil {
    private static final String JWT_TOKEN_PREFIX = "Bearer ";

    public static String extractAccessToken(String authHeaderValue) {
        if (hasTokenInHeader(authHeaderValue)) {
            return authHeaderValue.substring(JWT_TOKEN_PREFIX.length());
        }
        return "";
    }

    public static boolean hasTokenInHeader(String authHeaderValue) {
        return authHeaderValue != null && authHeaderValue.startsWith(JWT_TOKEN_PREFIX);
    }
}
