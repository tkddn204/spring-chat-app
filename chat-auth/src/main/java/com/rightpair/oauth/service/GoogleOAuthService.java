package com.rightpair.oauth.service;

import com.rightpair.domain.member.Member;
import com.rightpair.domain.member.MemberOpenAuth;
import com.rightpair.dto.OAuthRegisterMemberRequest;
import com.rightpair.dto.OAuthRegisterMemberResponse;
import com.rightpair.repository.member.MemberOpenAuthRepository;
import com.rightpair.security.JwtPrincipal;
import com.rightpair.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GoogleOAuthService extends DefaultOAuth2UserService {

    private final AuthService authService;
    private final MemberOpenAuthRepository memberOpenAuthRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");

        Optional<MemberOpenAuth> memberOpenAuthOptional = memberOpenAuthRepository.findByMemberEmail(email);

        Member member;
        if (memberOpenAuthOptional.isPresent()) {
            MemberOpenAuth memberOpenAuth = memberOpenAuthOptional.get();
            memberOpenAuth.updateProviderId(providerId);
            member = memberOpenAuth.getMember();
        } else {
            OAuthRegisterMemberResponse oAuthRegisterMemberResponse =
                    authService.oAuthRegisterMember(OAuthRegisterMemberRequest.from(oAuth2User, provider));
            member = oAuthRegisterMemberResponse.member();
        }

        return JwtPrincipal.from(
                String.valueOf(member.getId()),
                member.getEmail(),
                member.getName(),
                member.getEnabled(),
                member.getRolesToStrList(),
                oAuth2User.getAttributes()
        );
    }
}
