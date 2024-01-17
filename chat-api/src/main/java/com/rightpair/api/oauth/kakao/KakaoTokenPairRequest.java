package com.rightpair.api.oauth.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoTokenPairRequest(
        String grantType,
        String clientId,
        String clientSecret,
        String redirectUri,
        String code
) {
    public static KakaoTokenPairRequest from(
            String clientId, String clientSecret, String redirectUri, String code
    ) {
        return new KakaoTokenPairRequest(
                "authorization_code",
                clientId,
                clientSecret,
                redirectUri,
                code
        );
    }
}
