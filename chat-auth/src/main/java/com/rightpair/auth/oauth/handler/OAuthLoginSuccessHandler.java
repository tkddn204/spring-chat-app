package com.rightpair.auth.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rightpair.auth.exception.MemberNotFoundException;
import com.rightpair.auth.jwt.JwtPair;
import com.rightpair.auth.security.JwtPrincipal;
import com.rightpair.auth.service.AuthService;
import com.rightpair.core.domain.member.Member;
import com.rightpair.core.repository.member.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        JwtPrincipal jwtPrincipal = (JwtPrincipal) authentication.getPrincipal();
        String id = jwtPrincipal.getMemberId();

        Member member = memberRepository.findById(Long.valueOf(id)).orElseThrow(MemberNotFoundException::new);
        JwtPair jwtPair = authService.createJwtPairAndSaveRefreshToken(member);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(jwtPair));
    }
}
