package com.rightpair.api.jwt.security;

import com.rightpair.api.exception.business.JwtVerifyException;
import com.rightpair.api.exception.filter.AppAuthenticationException;
import com.rightpair.api.jwt.dto.JwtPayload;
import com.rightpair.api.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtService jwtService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String accessToken = (String) authentication.getCredentials();
        try {
            JwtPayload jwtPayload = jwtService.verifyAccessToken(accessToken);
            JwtPrincipal jwtPrincipal = (JwtPrincipal) jwtUserDetailsService.loadUserByUsername(jwtPayload.email());
            return JwtAuthenticationToken.authenticated(jwtPrincipal);
        } catch (JwtVerifyException e) {
            throw new AppAuthenticationException(e.getErrorCode());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthenticationToken.class);
    }
}
