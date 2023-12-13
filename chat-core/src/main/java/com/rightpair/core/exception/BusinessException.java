package com.rightpair.core.exception;

import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {

    protected ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
