package com.rightpair.controller.request;

import com.rightpair.util.JwtUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RefreshAccessTokenControllerRequest(
        @NotBlank(message = "리프레시 토큰을 넣어야 합니다.")
        @Pattern(message = "리프레시 토큰의 형식이 올바르지 않습니다.", regexp = JwtUtil.JWT_REGEX)
        String refreshToken
) {
}
