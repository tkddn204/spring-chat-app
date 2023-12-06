package com.rightpair.exception;

public record ErrorMessage(
        String code,
        String message
) {
    public static ErrorMessage create(ErrorCode errorCode, String message) {
        return new ErrorMessage(errorCode.name(), message);
    }
}
