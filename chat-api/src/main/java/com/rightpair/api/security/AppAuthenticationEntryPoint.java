package com.rightpair.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rightpair.api.exception.filter.AppAuthenticationException;
import com.rightpair.core.exception.ErrorCode;
import com.rightpair.core.exception.ErrorMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AppAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorCode errorCode;
        if (authException instanceof AppAuthenticationException) {
            errorCode = ((AppAuthenticationException) authException).getErrorCode();
        } else {
            errorCode = ErrorCode.INVALID_AUTH_ERROR;
        }
        ErrorMessage errorMessage = ErrorMessage.create(errorCode, authException.getMessage());
        objectMapper.writeValue(response.getOutputStream(), errorMessage);
    }
}
