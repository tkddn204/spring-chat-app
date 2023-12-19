package com.rightpair.api.util;

import java.util.regex.Pattern;

public class JwtUtil {
    public static final String JWT_REGEX = "^([a-zA-Z0-9_=]+)\\.([a-zA-Z0-9_=]+)\\.([a-zA-Z0-9_\\-\\+\\/=]*)";
    private static final String JWT_TOKEN_PREFIX = "Bearer ";

    public static String extractAccessToken(String authHeaderValue) {
        if (hasTokenInHeader(authHeaderValue)) {
            String rest = authHeaderValue.substring(JWT_TOKEN_PREFIX.length());
            if (Pattern.matches(JWT_REGEX, rest)) {
                return rest;
            }
        }
        return null;
    }

    public static boolean hasTokenInHeader(String authHeaderValue) {
        return authHeaderValue != null && authHeaderValue.startsWith(JWT_TOKEN_PREFIX);
    }
}
