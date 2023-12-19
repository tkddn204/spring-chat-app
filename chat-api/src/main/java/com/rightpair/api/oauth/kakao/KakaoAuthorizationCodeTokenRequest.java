package com.rightpair.api.oauth.kakao;

import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;

import java.io.IOException;
import java.util.Collection;

public class KakaoAuthorizationCodeTokenRequest extends AuthorizationCodeTokenRequest {

    public KakaoAuthorizationCodeTokenRequest(HttpTransport transport, JsonFactory jsonFactory, String clientId, String clientSecret, String code, String redirectUri) {
        super(transport, jsonFactory, new GenericUrl("https://kauth.kakao.com/oauth/token"), code);
        this.setClientAuthentication(new ClientParametersAuthentication(clientId, clientSecret));
        this.setRedirectUri(redirectUri);
    }

    public KakaoTokenResponse execute() throws IOException {
        return this.executeUnparsed().parseAs(KakaoTokenResponse.class);
    }

    @Override
    public KakaoAuthorizationCodeTokenRequest setRequestInitializer(HttpRequestInitializer requestInitializer) {
        return (KakaoAuthorizationCodeTokenRequest) super.setRequestInitializer(requestInitializer);
    }

    @Override
    public KakaoAuthorizationCodeTokenRequest setTokenServerUrl(GenericUrl tokenServerUrl) {
        return (KakaoAuthorizationCodeTokenRequest) super.setTokenServerUrl(tokenServerUrl);
    }

    @Override
    public KakaoAuthorizationCodeTokenRequest setScopes(Collection<String> scopes) {
        return (KakaoAuthorizationCodeTokenRequest) super.setScopes(scopes);
    }

    @Override
    public KakaoAuthorizationCodeTokenRequest setGrantType(String grantType) {
        return (KakaoAuthorizationCodeTokenRequest) super.setGrantType(grantType);
    }

    @Override
    public KakaoAuthorizationCodeTokenRequest setClientAuthentication(HttpExecuteInterceptor clientAuthentication) {
        return (KakaoAuthorizationCodeTokenRequest) super.setClientAuthentication(clientAuthentication);
    }

    @Override
    public KakaoAuthorizationCodeTokenRequest setResponseClass(Class<? extends TokenResponse> responseClass) {
        return (KakaoAuthorizationCodeTokenRequest) super.setResponseClass(responseClass);
    }

    @Override
    public KakaoAuthorizationCodeTokenRequest setCode(String code) {
        return (KakaoAuthorizationCodeTokenRequest) super.setCode(code);
    }

    @Override
    public KakaoAuthorizationCodeTokenRequest setRedirectUri(String redirectUri) {
        return (KakaoAuthorizationCodeTokenRequest) super.setRedirectUri(redirectUri);
    }

    @Override
    public KakaoAuthorizationCodeTokenRequest set(String fieldName, Object value) {
        return (KakaoAuthorizationCodeTokenRequest) super.set(fieldName, value);
    }
}
