package com.rightpair.api.oauth.service;

import com.rightpair.api.exception.MemberNotFoundException;
import com.rightpair.api.jwt.JwtPair;
import com.rightpair.api.oauth.kakao.KakaoIdToken;
import com.rightpair.api.oauth.resource.KakaoOAuthResourceRequestService;
import com.rightpair.api.service.AuthService;
import com.rightpair.api.service.request.OAuthRegisterMemberRequest;
import com.rightpair.api.service.response.RegisterMemberResponse;
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
public class KakaoOAuthService implements OAuthService {
    private final KakaoOAuthResourceRequestService kakaoOAuthResourceRequestService;
    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final MemberOpenAuthRepository memberOpenAuthRepository;

    @Override
    public JwtPair provideMemberJwtByOAuthCode(String code) {
        KakaoIdToken kakaoIdToken = kakaoOAuthResourceRequestService.getKakaoIdToken(code);
        Optional<MemberOpenAuth> memberOpenAuth =
                memberOpenAuthRepository.findByMemberEmail(kakaoIdToken.getPayload().getEmail());

        if (memberOpenAuth.isEmpty()) {
            RegisterMemberResponse registerMemberResponse = authService.oAuthRegisterMember(
                    OAuthRegisterMemberRequest.from(kakaoIdToken, OauthProvider.KAKAO.name()));
            return JwtPair.create(registerMemberResponse.accessToken(), registerMemberResponse.refreshToken());
        } else {
            Member member = memberRepository.findById(memberOpenAuth.get().getId())
                    .orElseThrow(MemberNotFoundException::new);
            return authService.createJwtPairAndSaveRefreshToken(member);
        }
    }
}
