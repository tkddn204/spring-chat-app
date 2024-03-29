package com.rightpair.api.dto.request;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.rightpair.api.dto.response.OAuthIdTokenPayload;
import com.rightpair.api.oauth.kakao.KakaoUserInfoResponse;

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

    public static OAuthRegisterMemberRequest from(KakaoUserInfoResponse kakaoUserInfoResponse, String provider) {
        return new OAuthRegisterMemberRequest(
                kakaoUserInfoResponse.kakaoAccount().email(),
                UUID.randomUUID().toString(),
                kakaoUserInfoResponse.kakaoAccount().profile().nickname(),
                provider,
                kakaoUserInfoResponse.id()
        );
    }
}
