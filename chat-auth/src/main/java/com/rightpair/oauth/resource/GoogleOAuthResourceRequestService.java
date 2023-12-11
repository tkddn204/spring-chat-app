package com.rightpair.oauth.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rightpair.dto.GoogleOAuthCertKeysResponse;
import com.rightpair.dto.GoogleOAuthResourceResponse;
import com.rightpair.exception.OAuthRestClientException;
import com.rightpair.oauth.util.CertKeyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.security.PublicKey;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GoogleOAuthResourceRequestService {
    private final static String GOOGLE_OAUTH_TOKEN_REQUEST_URL = "https://oauth2.googleapis.com/token";
    private final static String GOOGLE_CERT_KEYS_URL = "https://www.googleapis.com/oauth2/v3/certs";
    private final static String GOOGLE_CERT_KEYS_JSON_FILE = "google_oauth_cert.json";
    private final ObjectMapper objectMapper;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleOAuthClientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleOAuthClientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleOauthRedirectURI;
    @Value("${spring.security.oauth2.client.registration.google.scope}")
    private String googleOAuthScope;

    public GoogleOAuthResourceResponse requestAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", googleOAuthClientId);
        params.add("client_secret", googleOAuthClientSecret);
        params.add("redirect_uri", googleOauthRedirectURI);
        params.add("grant_type", "authorization_code");

        try {
            ResponseEntity<GoogleOAuthResourceResponse> response = restTemplate.postForEntity(
                    GOOGLE_OAUTH_TOKEN_REQUEST_URL, params, GoogleOAuthResourceResponse.class);
            return response.getBody();
        } catch (RestClientResponseException e) {
            throw new OAuthRestClientException(e.getMessage());
        }
    }

    public List<PublicKey> getCertKeys(String code) {
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(GOOGLE_CERT_KEYS_JSON_FILE)) {
            ObjectMapper mapper = new ObjectMapper();
            GoogleOAuthCertKeysResponse jsonObj = mapper.readValue(in, GoogleOAuthCertKeysResponse.class);
            return jsonObj.keys().stream().map(CertKeyUtils::getPublicKey).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
