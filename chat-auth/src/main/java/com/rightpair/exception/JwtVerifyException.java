package com.rightpair.exception;

public class JwtVerifyException extends RuntimeException {
    public JwtVerifyException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
