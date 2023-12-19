package com.rightpair.api.oauth.kakao;

import com.google.api.client.auth.oauth2.*;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Collection;

@Getter
public class KakaoAuthorizationCodeFlow extends AuthorizationCodeFlow {
    private final String prompt;
    private final String loginHint;
    private final String serviceTerms;
    private final String nonce;

    protected KakaoAuthorizationCodeFlow(Builder builder) {
        super(builder);
        this.prompt = builder.prompt;
        this.loginHint = builder.loginHint;
        this.serviceTerms = builder.serviceTerms;
        this.nonce = builder.nonce;
    }

    @Override
    public AuthorizationCodeRequestUrl newAuthorizationUrl() {
        return (new KakaoAuthorizationCodeRequestUrl(
                this.getAuthorizationServerEncodedUrl(),
                this.getClientId(),
                "",
                this.getScopes()
        ))
                .setOptionalParams(prompt, loginHint, serviceTerms, nonce);
    }

    public KakaoAuthorizationCodeTokenRequest newTokenRequest(String authorizationCode) {
        return (new KakaoAuthorizationCodeTokenRequest(
                this.getTransport(),
                this.getJsonFactory(),
                "",
                "",
                authorizationCode,
                ""
        ))
                .setClientAuthentication(this.getClientAuthentication())
                .setRequestInitializer(this.getRequestInitializer())
                .setScopes(this.getScopes());
    }

    @Getter
    @Setter
    public static class Builder extends AuthorizationCodeFlow.Builder {
        private String prompt;
        private String loginHint;
        private String serviceTerms;
        private String nonce;

        public Builder(HttpTransport transport, JsonFactory jsonFactory, String clientId, String clientSecret, Collection<String> scopes) {
            super(
                    BearerToken.authorizationHeaderAccessMethod(),
                    transport,
                    jsonFactory,
                    new GenericUrl("https://kauth.kakao.com/oauth/token"),
                    new ClientParametersAuthentication(clientId, clientSecret),
                    clientId,
                    "https://kauth.kakao.com/oauth/authorize"
            );
            this.setScopes(scopes);
        }

        public KakaoAuthorizationCodeFlow build() {
            return new KakaoAuthorizationCodeFlow(this);
        }

        public KakaoAuthorizationCodeFlow.Builder setDataStoreFactory(DataStoreFactory dataStore) throws IOException {
            return (KakaoAuthorizationCodeFlow.Builder) super.setDataStoreFactory(dataStore);
        }

        public KakaoAuthorizationCodeFlow.Builder setCredentialDataStore(DataStore<StoredCredential> typedDataStore) {
            return (KakaoAuthorizationCodeFlow.Builder) super.setCredentialDataStore(typedDataStore);
        }

        public KakaoAuthorizationCodeFlow.Builder setCredentialCreatedListener(AuthorizationCodeFlow.CredentialCreatedListener credentialCreatedListener) {
            return (KakaoAuthorizationCodeFlow.Builder) super.setCredentialCreatedListener(credentialCreatedListener);
        }

        public KakaoAuthorizationCodeFlow.Builder setRequestInitializer(HttpRequestInitializer requestInitializer) {
            return (KakaoAuthorizationCodeFlow.Builder) super.setRequestInitializer(requestInitializer);
        }

        public KakaoAuthorizationCodeFlow.Builder setScopes(Collection<String> scopes) {
            Preconditions.checkState(!scopes.isEmpty());
            return (KakaoAuthorizationCodeFlow.Builder) super.setScopes(scopes);
        }

        public KakaoAuthorizationCodeFlow.Builder setMethod(Credential.AccessMethod method) {
            return (KakaoAuthorizationCodeFlow.Builder) super.setMethod(method);
        }

        public KakaoAuthorizationCodeFlow.Builder setTransport(HttpTransport transport) {
            return (KakaoAuthorizationCodeFlow.Builder) super.setTransport(transport);
        }

        public KakaoAuthorizationCodeFlow.Builder setJsonFactory(JsonFactory jsonFactory) {
            return (KakaoAuthorizationCodeFlow.Builder) super.setJsonFactory(jsonFactory);
        }

        public KakaoAuthorizationCodeFlow.Builder setTokenServerUrl(GenericUrl tokenServerUrl) {
            return (KakaoAuthorizationCodeFlow.Builder) super.setTokenServerUrl(tokenServerUrl);
        }

        public KakaoAuthorizationCodeFlow.Builder setClientAuthentication(HttpExecuteInterceptor clientAuthentication) {
            return (KakaoAuthorizationCodeFlow.Builder) super.setClientAuthentication(clientAuthentication);
        }

        public KakaoAuthorizationCodeFlow.Builder setClientId(String clientId) {
            return (KakaoAuthorizationCodeFlow.Builder) super.setClientId(clientId);
        }

        public KakaoAuthorizationCodeFlow.Builder setAuthorizationServerEncodedUrl(String authorizationServerEncodedUrl) {
            return (KakaoAuthorizationCodeFlow.Builder) super.setAuthorizationServerEncodedUrl(authorizationServerEncodedUrl);
        }

        public KakaoAuthorizationCodeFlow.Builder setClock(Clock clock) {
            return (KakaoAuthorizationCodeFlow.Builder) super.setClock(clock);
        }

        public KakaoAuthorizationCodeFlow.Builder addRefreshListener(CredentialRefreshListener refreshListener) {
            return (KakaoAuthorizationCodeFlow.Builder) super.addRefreshListener(refreshListener);
        }

        public KakaoAuthorizationCodeFlow.Builder setRefreshListeners(Collection<CredentialRefreshListener> refreshListeners) {
            return (KakaoAuthorizationCodeFlow.Builder) super.setRefreshListeners(refreshListeners);
        }
    }
}
