package com.rightpair.api.exception;

import com.rightpair.core.exception.BusinessException;
import com.rightpair.core.exception.ErrorCode;

public class MemberWrongPasswordException extends BusinessException {
    private final static ErrorCode ERROR_CODE = ErrorCode.MEMBER_WRONG_PASSWORD_ERROR;
    public MemberWrongPasswordException() {
        super(ERROR_CODE);
    }
}
