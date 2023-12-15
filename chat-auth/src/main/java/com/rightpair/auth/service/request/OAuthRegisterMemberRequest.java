package com.rightpair.auth.service.request;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.rightpair.auth.service.response.OAuthIdTokenPayload;

import java.util.UUID;

public record OAuthRegisterMemberRequest(
        String email,
        String password,
        String name,
        String provider,
        String providerId
) {
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
