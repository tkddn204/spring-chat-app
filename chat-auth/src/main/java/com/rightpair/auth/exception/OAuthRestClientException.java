package com.rightpair.auth.exception;

import com.rightpair.core.exception.BusinessException;
import com.rightpair.core.exception.ErrorCode;
import lombok.Getter;

@Getter
public class OAuthRestClientException extends BusinessException {
    private final static ErrorCode ERROR_CODE = ErrorCode.OAUTH_REST_CLIENT_ERROR;

    public OAuthRestClientException(String errorMessage) {
        super(ERROR_CODE, ERROR_CODE.getMessage() + ": " + errorMessage);
    }
}
