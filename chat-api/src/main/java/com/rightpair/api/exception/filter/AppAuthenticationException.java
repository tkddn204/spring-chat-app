package com.rightpair.api.exception.filter;

import com.rightpair.core.exception.ErrorCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class AppAuthenticationException extends AuthenticationException {

    private final ErrorCode errorCode;

    public AppAuthenticationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
