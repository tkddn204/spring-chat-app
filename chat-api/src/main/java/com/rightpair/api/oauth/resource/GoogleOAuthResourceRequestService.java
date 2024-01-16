package com.rightpair.api.oauth.resource;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.rightpair.api.exception.business.OAuthRestClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GoogleOAuthResourceRequestService {
    private static final List<String> GOOGLE_SCOPES = Arrays.asList(
            "openid",
            "https://www.googleapis.com/auth/userinfo.profile",
            "https://www.googleapis.com/auth/userinfo.email");


    @Value("${service.oauth.google.client-id}")
    private String googleOAuthClientId;

    @Value("${service.oauth.google.client-secret}")
    private String googleOAuthClientSecret;

    @Value("${service.oauth.google.redirect-uri}")
    private String googleOauthRedirectURI;

    public GoogleIdToken getGoogleIdToken(String code) {
        try {
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance(),
                    googleOAuthClientId, googleOAuthClientSecret,
                    GOOGLE_SCOPES).build();

            GoogleAuthorizationCodeTokenRequest request = flow.newTokenRequest(code);
            request.setRedirectUri(googleOauthRedirectURI);
            GoogleTokenResponse googleTokenResponse = request.execute();

            return googleTokenResponse.parseIdToken();
        } catch (Exception e) {
            throw new OAuthRestClientException(e.getMessage());
        }
    }
}
