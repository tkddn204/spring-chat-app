package com.rightpair.auth.exception;

import com.rightpair.core.exception.ErrorCode;
import lombok.Getter;

@Getter
public class OAuthRestClientException extends RuntimeException {
    private final static ErrorCode ERROR_CODE = ErrorCode.OAUTH_REST_CLIENT_ERROR;

    private final ErrorCode errorCode;

    public OAuthRestClientException() {
        super(ERROR_CODE.getMessage());
        this.errorCode = ERROR_CODE;
    }

    public OAuthRestClientException(String errorMessage) {
        super(ERROR_CODE.getMessage() + ": " + errorMessage);
        this.errorCode = ERROR_CODE;
    }
}
