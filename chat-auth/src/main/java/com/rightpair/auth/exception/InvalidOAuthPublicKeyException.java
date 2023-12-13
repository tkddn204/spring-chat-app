package com.rightpair.auth.exception;

import com.rightpair.core.exception.BusinessException;
import com.rightpair.core.exception.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidOAuthPublicKeyException extends BusinessException {
    private final static ErrorCode errorCode = ErrorCode.OAUTH_INVALID_PUBLIC_KEY_ERROR;

    public InvalidOAuthPublicKeyException() {
        super(errorCode);
    }
}
