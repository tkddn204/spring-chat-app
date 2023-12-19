package com.rightpair.api.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private JwtPrincipal jwtPrincipal;
    private String credentials;

    public JwtAuthenticationToken(JwtPrincipal jwtPrincipal, boolean isAuthenticated) {
        super(jwtPrincipal.getAuthorities());
        this.jwtPrincipal = jwtPrincipal;
        setAuthenticated(isAuthenticated);
    }

    public JwtAuthenticationToken(String credentials, boolean isAuthenticated) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.credentials = credentials;
        setAuthenticated(isAuthenticated);
    }

    public static JwtAuthenticationToken unauthenticated(String accessToken) {
        return new JwtAuthenticationToken(accessToken,false);
    }

    public static JwtAuthenticationToken authenticated(JwtPrincipal jwtPrincipal) {
        return new JwtAuthenticationToken(jwtPrincipal,true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.jwtPrincipal;
    }
}
