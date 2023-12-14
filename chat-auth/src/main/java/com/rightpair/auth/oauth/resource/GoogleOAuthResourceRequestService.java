package com.rightpair.auth.oauth.resource;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.rightpair.auth.exception.OAuthRestClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class GoogleOAuthResourceRequestService {
    private final static String GOOGLE_CLIENT_SECRET_JSON_FILE = "client_secret.json";

    private static final List<String> GOOGLE_SCOPES = Arrays.asList(
            "openid",
            "https://www.googleapis.com/auth/userinfo.profile",
            "https://www.googleapis.com/auth/userinfo.email");


    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleOauthRedirectURI;

    public GoogleIdToken getGoogleIdToken(String code) {
        try (InputStream in = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(GOOGLE_CLIENT_SECRET_JSON_FILE)) {
            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

            GoogleClientSecrets clientSecrets =
                    GoogleClientSecrets.load(jsonFactory, new InputStreamReader(Objects.requireNonNull(in)));
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    jsonFactory,
                    clientSecrets,
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
