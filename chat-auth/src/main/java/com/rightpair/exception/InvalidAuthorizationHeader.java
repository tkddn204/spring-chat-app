package com.rightpair.exception;

import lombok.Getter;

@Getter
public class InvalidAuthorizationHeader extends RuntimeException {
    private final static ErrorCode ERROR_CODE = ErrorCode.INVALID_AUTHORIZATION_HEADER;

    private final ErrorCode errorCode;

    public InvalidAuthorizationHeader() {
        super(ERROR_CODE.getMessage());
        this.errorCode = ERROR_CODE;
    }
}
