package com.rightpair.api.exception.business;

import com.rightpair.core.exception.BusinessException;
import com.rightpair.core.exception.ErrorCode;

public class RoleNotFoundException extends BusinessException {
    private final static ErrorCode ERROR_CODE = ErrorCode.ROLE_NOT_FOUND_ERROR;
    public RoleNotFoundException() {
        super(ERROR_CODE);
    }
}
