package com.rightpair.api.exception.filter;

import com.rightpair.core.exception.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidAuthorizationHeader extends AppAuthenticationException {
    private final static ErrorCode ERROR_CODE = ErrorCode.INVALID_AUTHORIZATION_HEADER;
    public InvalidAuthorizationHeader() {
        super(ERROR_CODE);
    }
}
