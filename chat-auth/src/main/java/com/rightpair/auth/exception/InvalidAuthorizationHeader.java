package com.rightpair.auth.exception;

import com.rightpair.core.exception.ErrorCode;
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
