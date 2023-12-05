package com.rightpair.service;

import com.rightpair.domain.member.Member;
import com.rightpair.dto.AuthenticateMemberRequest;
import com.rightpair.dto.AuthenticateMemberResponse;
import com.rightpair.dto.RegisterMemberRequest;
import com.rightpair.dto.RegisterMemberResponse;
import com.rightpair.exception.MemberAlreadyExistedException;
import com.rightpair.exception.MemberNotFoundException;
import com.rightpair.exception.MemberWrongPasswordException;
import com.rightpair.jwt.dto.JwtPayload;
import com.rightpair.jwt.service.JwtService;
import com.rightpair.repository.MemberAuthTokenRepository;
import com.rightpair.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final MemberAuthTokenRepository memberAuthTokenRepository;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthenticateMemberResponse authenticateMember(AuthenticateMemberRequest request) {
        Member storedMember = memberRepository.findByEmail(request.email())
                .orElseThrow(MemberNotFoundException::new);

        if (!passwordEncoder.matches(request.password(), storedMember.getPassword())) {
            throw new MemberWrongPasswordException();
        }

        return AuthenticateMemberResponse.fromJwtPair(
                jwtService.createTokenPair(JwtPayload.from(
                        String.valueOf(storedMember.getId()), storedMember.getEmail())));
    }

    @Transactional
    public RegisterMemberResponse registerMember(RegisterMemberRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new MemberAlreadyExistedException();
        }

        Member savedMember = memberRepository.save(
                Member.create(request.email(), request.password(), request.name()));

        return RegisterMemberResponse.fromJwtPair(
                jwtService.createTokenPair(JwtPayload.from(
                        String.valueOf(savedMember.getId()), savedMember.getEmail())));
    }

    @Transactional
    public void unAuthenticateMember(Long memberId) {
        memberAuthTokenRepository.deleteByMemberId(memberId);
    }
}
