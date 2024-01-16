package com.rightpair.api.exception.business;

import com.rightpair.core.exception.BusinessException;
import com.rightpair.core.exception.ErrorCode;

public class MemberNotFoundException extends BusinessException {
    private final static ErrorCode ERROR_CODE = ErrorCode.MEMBER_NOT_FOUND_ERROR;
    public MemberNotFoundException() {
        super(ERROR_CODE);
    }
}
