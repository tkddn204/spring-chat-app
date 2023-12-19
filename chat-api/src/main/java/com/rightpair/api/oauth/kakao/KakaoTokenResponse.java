package com.rightpair.api.oauth.kakao;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.util.Key;
import lombok.Getter;

import java.io.IOException;

@Getter
public class KakaoTokenResponse extends TokenResponse {
    @Key("id_token")
    private String idToken;

    @Override
    public KakaoTokenResponse setAccessToken(String accessToken) {
        return (KakaoTokenResponse) super.setAccessToken(accessToken);
    }

    @Override
    public KakaoTokenResponse setTokenType(String tokenType) {
        return (KakaoTokenResponse) super.setTokenType(tokenType);
    }

    @Override
    public KakaoTokenResponse setExpiresInSeconds(Long expiresInSeconds) {
        return (KakaoTokenResponse) super.setExpiresInSeconds(expiresInSeconds);
    }

    @Override
    public KakaoTokenResponse setRefreshToken(String refreshToken) {
        return (KakaoTokenResponse) super.setRefreshToken(refreshToken);
    }

    @Override
    public KakaoTokenResponse setScope(String scope) {
        return (KakaoTokenResponse) super.setScope(scope);
    }

    public KakaoIdToken parseIdToken() throws IOException {
        return KakaoIdToken.parse(this.getFactory(), this.getIdToken());
    }

    @Override
    public KakaoTokenResponse set(String fieldName, Object value) {
        return (KakaoTokenResponse) super.set(fieldName, value);
    }

    @Override
    public KakaoTokenResponse clone() {
        return (KakaoTokenResponse) super.clone();
    }
}
