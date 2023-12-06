package com.rightpair.exception;

import lombok.Getter;

@Getter
public class JwtVerifyException extends BusinessException {
    private final ErrorCode errorCode;
    public JwtVerifyException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
