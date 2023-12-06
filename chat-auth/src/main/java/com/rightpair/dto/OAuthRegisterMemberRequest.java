package com.rightpair.dto;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.UUID;

public record OAuthRegisterMemberRequest(
        String email,
        String password,
        String name,
        String provider,
        String providerId
) {

    public static OAuthRegisterMemberRequest from(OAuth2User oAuth2User, String provider) {
        return new OAuthRegisterMemberRequest(
                oAuth2User.getAttribute("email"),
                UUID.randomUUID().toString(),
                oAuth2User.getAttribute("name"),
                provider,
                oAuth2User.getAttribute("sub")
        );
    }
}
