package com.rightpair.api.oauth.resource;

import com.google.api.client.http.apache.v2.ApacheHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.rightpair.api.exception.OAuthRestClientException;
import com.rightpair.api.oauth.kakao.KakaoAuthorizationCodeFlow;
import com.rightpair.api.oauth.kakao.KakaoAuthorizationCodeTokenRequest;
import com.rightpair.api.oauth.kakao.KakaoIdToken;
import com.rightpair.api.oauth.kakao.KakaoTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class KakaoOAuthResourceRequestService {
    private static final List<String> KAKAO_SCOPES = Arrays.asList("openid", "profile_image", "profile_nickname", "account_email");

    @Value("${service.oauth.kakao.client-id}")
    private String kakaoOAuthClientId;

    @Value("${service.oauth.kakao.client-secret}")
    private String kakaoOAuthClientSecret;

    @Value("${service.oauth.kakao.redirect-uri}")
    private String kakaoOauthRedirectURI;


    public KakaoIdToken getKakaoIdToken(String code) {
        try {
            KakaoAuthorizationCodeFlow flow = new KakaoAuthorizationCodeFlow.Builder(
                    new ApacheHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    kakaoOAuthClientId,
                    kakaoOAuthClientSecret,
                    KAKAO_SCOPES).build();

            KakaoAuthorizationCodeTokenRequest request = flow.newTokenRequest(code);
            request.setRedirectUri(kakaoOauthRedirectURI);
            KakaoTokenResponse kakaoTokenResponse = request.execute();

            return kakaoTokenResponse.parseIdToken();
        } catch (Exception e) {
            throw new OAuthRestClientException(e.getMessage());
        }
    }
}
