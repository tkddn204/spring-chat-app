package com.rightpair.exception;

import lombok.Getter;

@Getter
public class JwtVerifyException extends RuntimeException {
    private final ErrorCode errorCode;
    public JwtVerifyException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
