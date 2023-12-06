package com.rightpair.exception;

public class RoleNotFoundException extends BusinessException {
    private final static ErrorCode ERROR_CODE = ErrorCode.ROLE_NOT_FOUND_ERROR;
    public RoleNotFoundException() {
        super(ERROR_CODE);
    }
}
