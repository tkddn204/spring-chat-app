package com.rightpair.api.oauth.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoTokenPairResponse(
        String tokenType,
        String accessToken,
        String refreshToken,
        Integer expiresIn,
        Integer refreshTokenExpiresIn,
        String scope
) {
}
