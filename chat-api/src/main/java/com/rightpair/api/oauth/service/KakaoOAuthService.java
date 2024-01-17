package com.rightpair.api.oauth.service;

import com.rightpair.api.dto.request.OAuthRegisterMemberRequest;
import com.rightpair.api.dto.response.RegisterMemberResponse;
import com.rightpair.api.exception.business.MemberNotFoundException;
import com.rightpair.api.jwt.JwtPair;
import com.rightpair.api.oauth.kakao.KakaoTokenPairResponse;
import com.rightpair.api.oauth.kakao.KakaoUserInfoResponse;
import com.rightpair.api.oauth.resource.KakaoOAuthResourceRequestService;
import com.rightpair.api.service.AuthService;
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
        KakaoTokenPairResponse kakaoTokenPair = kakaoOAuthResourceRequestService.getKakaoTokenPair(code);
        KakaoUserInfoResponse kakaoUserInfoResponse = kakaoOAuthResourceRequestService.getKakaoUserInfo(kakaoTokenPair);

        Optional<MemberOpenAuth> memberOpenAuth =
                memberOpenAuthRepository.findByMemberEmail(kakaoUserInfoResponse.kakaoAccount().email());

        if (memberOpenAuth.isEmpty()) {
            RegisterMemberResponse registerMemberResponse = authService.oAuthRegisterMember(
                    OAuthRegisterMemberRequest.from(kakaoUserInfoResponse, OauthProvider.KAKAO.name()));
            return JwtPair.create(registerMemberResponse.accessToken(), registerMemberResponse.refreshToken());
        } else {
            Member member = memberRepository.findById(memberOpenAuth.get().getId())
                    .orElseThrow(MemberNotFoundException::new);
            return authService.createJwtPairAndSaveRefreshToken(member);
        }
    }
}
