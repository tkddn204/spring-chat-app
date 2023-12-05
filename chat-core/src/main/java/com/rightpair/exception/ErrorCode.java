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

    // 멤버
    MEMBER_NOT_FOUND_ERROR("해당 멤버가 존재하지 않습니다."),
    MEMBER_WRONG_PASSWORD_ERROR("멤버의 비밀번호가 일치하지 않습니다."),
    MEMBER_ALREADY_EXISTED_ERROR("이미 존재하는 멤버입니다."),

    // 공통
    INTERNAL_SERVICE_ERROR("내부 서비스 에러입니다.");

    private final String message;
}
