package com.rightpair.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // JWT
    JWT_EXPIRED_ERROR("만료된 JWT입니다."),
    JWT_INVALID_SIGNATURE_ERROR("JWT의 시그니처가 올바르지 않습니다."),
    JWT_MALFORMED_ERROR("JWT의 시그니처가 올바르지 않습니다."),
    JWT_UNSUPPORTED_ERROR("지원하지 않는 형식의 JWT입니다."),


    // 공통
    INTERNAL_SERVICE_ERROR("내부 서비스 에러입니다.");

    private final String message;
}
