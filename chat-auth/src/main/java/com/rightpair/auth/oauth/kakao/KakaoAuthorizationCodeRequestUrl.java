package com.rightpair.auth.oauth.kakao;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.util.Key;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class KakaoAuthorizationCodeRequestUrl extends AuthorizationCodeRequestUrl {

    @Key
    private String prompt;

    @Key("login_hint")
    private String loginHint;

    @Key("service_terms")
    private String serviceTerms;

    @Key
    private String nonce;


    public KakaoAuthorizationCodeRequestUrl(String clientId, String redirectUri, Collection<String> scopes) {
        this("https://kauth.kakao.com/oauth/authorize", clientId, redirectUri, scopes);
    }

    public KakaoAuthorizationCodeRequestUrl(String authorizationServerEncodedUrl, String clientId, String redirectUri, Collection<String> scopes) {
        super(authorizationServerEncodedUrl, clientId);
        this.setRedirectUri(redirectUri);
        this.setScopes(scopes);
    }

    public KakaoAuthorizationCodeRequestUrl setOptionalParams(String prompt, String loginHint, String serviceTerms, String nonce) {
        this.prompt = prompt;
        this.loginHint = loginHint;
        this.serviceTerms = serviceTerms;
        this.nonce = nonce;
        return this;
    }
}
