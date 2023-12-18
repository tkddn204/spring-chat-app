package com.rightpair.auth.oauth.service;

import com.rightpair.auth.jwt.JwtPair;

public interface OAuthService {
    JwtPair provideMemberJwtByOAuthCode(String code);
}
