package com.rightpair.api.service;

import com.rightpair.api.exception.MemberAlreadyExistedException;
import com.rightpair.api.exception.MemberNotFoundException;
import com.rightpair.api.exception.MemberWrongPasswordException;
import com.rightpair.api.exception.RoleNotFoundException;
import com.rightpair.api.jwt.JwtPair;
import com.rightpair.api.jwt.dto.JwtPayload;
import com.rightpair.api.jwt.service.JwtService;
import com.rightpair.api.service.request.AuthenticateMemberRequest;
import com.rightpair.api.service.request.OAuthRegisterMemberRequest;
import com.rightpair.api.service.request.RegisterMemberRequest;
import com.rightpair.api.service.response.AuthenticateMemberResponse;
import com.rightpair.api.service.response.GetMemberResponse;
import com.rightpair.api.service.response.RegisterMemberResponse;
import com.rightpair.core.domain.member.*;
import com.rightpair.core.repository.member.*;
import com.rightpair.core.type.OauthProvider;
import com.rightpair.core.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final MemberAuthTokenRepository memberAuthTokenRepository;
    private final MemberRepository memberRepository;
    private final MemberOpenAuthRepository memberOpenAuthRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthenticateMemberResponse authenticateMember(AuthenticateMemberRequest request) {
        Member storedMember = memberRepository.findByEmail(request.email())
                .orElseThrow(MemberNotFoundException::new);

        if (!passwordEncoder.matches(request.password(), storedMember.getPassword())) {
            throw new MemberWrongPasswordException();
        }

        return AuthenticateMemberResponse.fromJwtPair(createJwtPairAndSaveRefreshToken(storedMember));
    }

    @Transactional
    public RegisterMemberResponse registerMember(RegisterMemberRequest request) {
        Member savedMember = registerToUserRole(request.email(), request.password(), request.name());

        return RegisterMemberResponse.fromJwtPair(createJwtPairAndSaveRefreshToken(savedMember));
    }

    @Transactional
    public RegisterMemberResponse oAuthRegisterMember(OAuthRegisterMemberRequest request) {
        Member savedMember = registerToUserRole(request.email(), request.password(), request.name());

        memberOpenAuthRepository.save(
                MemberOpenAuth.create(savedMember, OauthProvider.GOOGLE, request.providerId()));

        return RegisterMemberResponse.fromJwtPair(createJwtPairAndSaveRefreshToken(savedMember));
    }

    private Member registerToUserRole(String email, String password, String name) {
        if (memberRepository.existsByEmail(email)) {
            throw new MemberAlreadyExistedException();
        }

        Member savedMember = memberRepository.save(Member.create(email, passwordEncoder.encode(password), name));
        Role role = roleRepository.findByRoleType(RoleType.USER).orElseThrow(RoleNotFoundException::new);
        memberRoleRepository.save(MemberRole.create(savedMember, role));
        return savedMember;
    }

    @Transactional
    public JwtPair createJwtPairAndSaveRefreshToken(Member member) {
        JwtPair jwtPair = jwtService.createTokenPair(
                JwtPayload.from(String.valueOf(member.getId()), member.getEmail()));
        memberAuthTokenRepository.save(MemberAuthToken.create(member, jwtPair.refreshToken(),
                LocalDateTime.now().plus(jwtService.getRefreshExpiration(), ChronoUnit.MILLIS)));
        return jwtPair;
    }

    @Transactional
    public void unAuthenticateMember(Long memberId) {
        memberAuthTokenRepository.deleteByMemberId(memberId);
    }

    @Transactional(readOnly = true)
    public GetMemberResponse getMemberByEmail(String email) {
        Member storedMember = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        return GetMemberResponse.create(storedMember);
    }

    @Transactional
    public AuthenticateMemberResponse refreshAccessToken(String refreshToken) {
        JwtPayload jwtPayload = jwtService.verifyRefreshToken(refreshToken);

        memberAuthTokenRepository.findByMemberId(Long.valueOf(jwtPayload.id()));

        return AuthenticateMemberResponse.fromJwtPair(jwtService.refreshAccessToken(refreshToken, jwtPayload));
    }
}
