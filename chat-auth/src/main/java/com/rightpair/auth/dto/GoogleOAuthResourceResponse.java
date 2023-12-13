package com.rightpair.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GoogleOAuthResourceResponse(
        String accessToken,
        String refreshToken,
        Long expiresIn,
        String scope,
        String tokenType,
        String IdToken
) {
}
