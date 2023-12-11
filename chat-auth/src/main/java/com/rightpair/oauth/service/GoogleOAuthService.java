package com.rightpair.oauth.service;

import com.rightpair.domain.member.Member;
import com.rightpair.domain.member.MemberOpenAuth;
import com.rightpair.dto.GoogleOAuthResourceResponse;
import com.rightpair.dto.OAuthIdTokenPayload;
import com.rightpair.dto.OAuthRegisterMemberRequest;
import com.rightpair.dto.RegisterMemberResponse;
import com.rightpair.exception.MemberNotFoundException;
import com.rightpair.jwt.JwtPair;
import com.rightpair.jwt.service.JwtService;
import com.rightpair.oauth.resource.GoogleOAuthResourceRequestService;
import com.rightpair.repository.member.MemberOpenAuthRepository;
import com.rightpair.repository.member.MemberRepository;
import com.rightpair.service.AuthService;
import com.rightpair.type.OauthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;
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
        GoogleOAuthResourceResponse response = googleOAuthResourceRequestService.requestAccessToken(code);
        List<PublicKey> oauthKeys = googleOAuthResourceRequestService.getCertKeys(code);
        OAuthIdTokenPayload payload = jwtService.decodeOauthIdToken(response.IdToken(), oauthKeys);

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
