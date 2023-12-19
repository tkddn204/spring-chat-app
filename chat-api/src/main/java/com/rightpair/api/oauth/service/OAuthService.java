package com.rightpair.api.oauth.service;

import com.rightpair.api.jwt.JwtPair;

public interface OAuthService {
    JwtPair provideMemberJwtByOAuthCode(String code);
}
