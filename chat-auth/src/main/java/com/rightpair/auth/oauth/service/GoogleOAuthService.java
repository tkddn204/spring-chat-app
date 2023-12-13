package com.rightpair.auth.oauth.service;

import com.rightpair.auth.exception.MemberNotFoundException;
import com.rightpair.auth.jwt.JwtPair;
import com.rightpair.auth.jwt.service.JwtService;
import com.rightpair.auth.oauth.resource.GoogleOAuthResourceRequestService;
import com.rightpair.auth.service.AuthService;
import com.rightpair.auth.service.request.OAuthRegisterMemberRequest;
import com.rightpair.auth.service.response.GoogleOAuthCertKeysResponse;
import com.rightpair.auth.service.response.GoogleOAuthResourceResponse;
import com.rightpair.auth.service.response.OAuthIdTokenPayload;
import com.rightpair.auth.service.response.RegisterMemberResponse;
import com.rightpair.core.domain.member.Member;
import com.rightpair.core.domain.member.MemberOpenAuth;
import com.rightpair.core.repository.member.MemberOpenAuthRepository;
import com.rightpair.core.repository.member.MemberRepository;
import com.rightpair.core.type.OauthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GoogleOAuthService {
    private final GoogleOAuthResourceRequestService googleOAuthResourceRequestService;
    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final MemberOpenAuthRepository memberOpenAuthRepository;

    public JwtPair provideMemberJwtByOAuthCode(String provider, String code) {
        GoogleOAuthResourceResponse resourceResponse = googleOAuthResourceRequestService.requestAccessToken(code);
        GoogleOAuthCertKeysResponse certKeysResponse = googleOAuthResourceRequestService.getGoogleOAuthCertKeys();

        String idToken = resourceResponse.IdToken();
        String oAuthIdTokenKeyId = jwtService.getOAuthIdTokenKeyId(idToken);
        PublicKey oAuthKey = googleOAuthResourceRequestService.getCertPublicKeys(
                certKeysResponse.keys(), oAuthIdTokenKeyId);
        OAuthIdTokenPayload payload = jwtService.decodeOauthIdToken(idToken, oAuthKey);

        Optional<MemberOpenAuth> memberOpenAuth = memberOpenAuthRepository.findByMemberEmail(payload.email());
        if (memberOpenAuth.isEmpty()) {
            RegisterMemberResponse registerMemberResponse = authService.oAuthRegisterMember(
                    OAuthRegisterMemberRequest.from(payload, OauthProvider.GOOGLE.name()));
            return JwtPair.create(registerMemberResponse.accessToken(), registerMemberResponse.refreshToken());
        } else {
            Member member = memberRepository.findById(memberOpenAuth.get().getId())
                    .orElseThrow(MemberNotFoundException::new);
            return authService.createJwtPairAndSaveRefreshToken(member);
        }
    }
}
