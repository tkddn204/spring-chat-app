package com.rightpair.auth.oauth.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.rightpair.auth.exception.MemberNotFoundException;
import com.rightpair.auth.jwt.JwtPair;
import com.rightpair.auth.oauth.resource.GoogleOAuthResourceRequestService;
import com.rightpair.auth.service.AuthService;
import com.rightpair.auth.service.request.OAuthRegisterMemberRequest;
import com.rightpair.auth.service.response.RegisterMemberResponse;
import com.rightpair.core.domain.member.Member;
import com.rightpair.core.domain.member.MemberOpenAuth;
import com.rightpair.core.repository.member.MemberOpenAuthRepository;
import com.rightpair.core.repository.member.MemberRepository;
import com.rightpair.core.type.OauthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GoogleOAuthService {
    private final GoogleOAuthResourceRequestService googleOAuthResourceRequestService;
    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final MemberOpenAuthRepository memberOpenAuthRepository;

    public JwtPair provideMemberJwtByOAuthCode(String provider, String code) {
        GoogleIdToken googleIdToken = googleOAuthResourceRequestService.getGoogleIdToken(code);
        Optional<MemberOpenAuth> memberOpenAuth =
                memberOpenAuthRepository.findByMemberEmail(googleIdToken.getPayload().getEmail());

        if (memberOpenAuth.isEmpty()) {
            RegisterMemberResponse registerMemberResponse = authService.oAuthRegisterMember(
                    OAuthRegisterMemberRequest.from(googleIdToken, OauthProvider.GOOGLE.name()));
            return JwtPair.create(registerMemberResponse.accessToken(), registerMemberResponse.refreshToken());
        } else {
            Member member = memberRepository.findById(memberOpenAuth.get().getId())
                    .orElseThrow(MemberNotFoundException::new);
            return authService.createJwtPairAndSaveRefreshToken(member);
        }
    }
}
