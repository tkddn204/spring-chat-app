package com.rightpair.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshAccessTokenControllerRequest(
        @NotBlank(message = "리프레시 토큰을 넣어야 합니다.")
        String refreshToken
) {
}
