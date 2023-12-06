package com.rightpair.exception;

public class MemberAlreadyExistedException extends BusinessException {
    private final static ErrorCode ERROR_CODE = ErrorCode.MEMBER_ALREADY_EXISTED_ERROR;
    public MemberAlreadyExistedException() {
        super(ERROR_CODE);
    }
}
