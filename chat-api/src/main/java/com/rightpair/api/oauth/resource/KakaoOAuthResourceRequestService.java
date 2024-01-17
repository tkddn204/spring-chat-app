package com.rightpair.api.oauth.resource;

import com.rightpair.api.exception.business.OAuthRestClientException;
import com.rightpair.api.oauth.kakao.KakaoTokenPairRequest;
import com.rightpair.api.oauth.kakao.KakaoTokenPairResponse;
import com.rightpair.api.oauth.kakao.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@Service
public class KakaoOAuthResourceRequestService {

    @Value("${service.oauth.kakao.user-info-url}")
    private String kakaoUserInfoURL;

    @Value("${service.oauth.kakao.token-url}")
    private String kakaoTokenURL;

    @Value("${service.oauth.kakao.client-id}")
    private String kakaoOAuthClientId;

    @Value("${service.oauth.kakao.client-secret}")
    private String kakaoOAuthClientSecret;

    @Value("${service.oauth.kakao.redirect-uri}")
    private String kakaoOauthRedirectURI;


    public KakaoTokenPairResponse getKakaoTokenPair(String code) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            URI kakaoUri = UriComponentsBuilder
                    .fromUriString(kakaoTokenURL)
                    .encode()
                    .build().toUri();

            KakaoTokenPairRequest request = KakaoTokenPairRequest.from(
                    kakaoOAuthClientId,
                    kakaoOAuthClientSecret,
                    kakaoOauthRedirectURI,
                    code
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
            requestMap.add("grant_type", request.grantType());
            requestMap.add("client_id", request.clientId());
            requestMap.add("client_secret", request.clientSecret());
            requestMap.add("redirect_uri", request.redirectUri());
            requestMap.add("code", request.code());

            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(requestMap, headers);
            return restTemplate.postForEntity(kakaoUri, httpEntity, KakaoTokenPairResponse.class).getBody();
        } catch (Exception e) {
            throw new OAuthRestClientException(e.getMessage());
        }
    }

    public KakaoUserInfoResponse getKakaoUserInfo(KakaoTokenPairResponse kakaoIdToken) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            URI kakaoUri = UriComponentsBuilder
                    .fromUriString(kakaoUserInfoURL)
                    .encode()
                    .build().toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(kakaoIdToken.accessToken());

            return restTemplate.exchange(kakaoUri,
                            HttpMethod.GET,
                            new HttpEntity<>(headers),
                            KakaoUserInfoResponse.class)
                    .getBody();
        } catch (Exception e) {
            throw new OAuthRestClientException(e.getMessage());
        }
    }
}
