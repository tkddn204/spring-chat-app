package com.rightpair.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rightpair.exception.ErrorCode;
import com.rightpair.exception.ErrorMessage;
import com.rightpair.exception.InvalidAuthorizationHeader;
import com.rightpair.exception.JwtVerifyException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtVerifyException e) {
            setErrorResponse(response, e.getErrorCode());
        } catch (InvalidAuthorizationHeader e) {
            setErrorResponse(response, e.getErrorCode());
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(response.getOutputStream(), ErrorMessage.create(errorCode, errorCode.getMessage()));
    }
}