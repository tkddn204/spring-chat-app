package com.rightpair.auth.exception;

import com.rightpair.core.exception.BusinessException;
import com.rightpair.core.exception.ErrorCode;
import lombok.Getter;

@Getter
public class JwtVerifyException extends BusinessException {
    private final ErrorCode errorCode;
    public JwtVerifyException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
