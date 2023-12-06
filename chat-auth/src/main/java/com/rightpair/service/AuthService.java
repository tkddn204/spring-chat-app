package com.rightpair.service;

import com.rightpair.domain.member.Member;
import com.rightpair.domain.member.MemberAuthToken;
import com.rightpair.domain.member.MemberRole;
import com.rightpair.domain.member.Role;
import com.rightpair.dto.*;
import com.rightpair.exception.MemberAlreadyExistedException;
import com.rightpair.exception.MemberNotFoundException;
import com.rightpair.exception.MemberWrongPasswordException;
import com.rightpair.exception.RoleNotFoundException;
import com.rightpair.jwt.JwtPair;
import com.rightpair.jwt.dto.JwtPayload;
import com.rightpair.jwt.service.JwtService;
import com.rightpair.repository.MemberAuthTokenRepository;
import com.rightpair.repository.MemberRepository;
import com.rightpair.repository.MemberRoleRepository;
import com.rightpair.repository.RoleRepository;
import com.rightpair.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final MemberAuthTokenRepository memberAuthTokenRepository;
    private final MemberRepository memberRepository;
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
        if (memberRepository.existsByEmail(request.email())) {
            throw new MemberAlreadyExistedException();
        }

        Member savedMember = memberRepository.save(Member.create(request.email(),
                        passwordEncoder.encode(request.password()),
                        request.name()));
        Role role = roleRepository.findByRoleType(RoleType.USER).orElseThrow(RoleNotFoundException::new);
        memberRoleRepository.save(MemberRole.create(savedMember, role));

        return RegisterMemberResponse.fromJwtPair(createJwtPairAndSaveRefreshToken(savedMember));
    }

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

        List<MemberRole> memberRoles = memberRoleRepository.findAllByMemberId(storedMember.getId());
        List<String> roles = memberRoles.stream().map(memberRole -> memberRole.getRole().getRoleType().name()).toList();

        return GetMemberResponse.create(storedMember, roles);
    }
}
