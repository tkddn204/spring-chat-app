package com.rightpair.auth.service.request;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.rightpair.auth.service.response.OAuthIdTokenPayload;
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

    public static OAuthRegisterMemberRequest from(OAuthIdTokenPayload payload, String provider) {
        return new OAuthRegisterMemberRequest(
                payload.email(),
                UUID.randomUUID().toString(),
                payload.name(),
                provider,
                payload.sub()
        );
    }

    public static OAuthRegisterMemberRequest from(GoogleIdToken googleIdToken, String provider) {
        GoogleIdToken.Payload payload = googleIdToken.getPayload();
        return new OAuthRegisterMemberRequest(
                payload.getEmail(),
                UUID.randomUUID().toString(),
                (String) payload.get("name"),
                provider,
                payload.getSubject()
        );
    }
}
