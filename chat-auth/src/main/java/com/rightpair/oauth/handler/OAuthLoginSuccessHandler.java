package com.rightpair.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rightpair.domain.member.Member;
import com.rightpair.exception.MemberNotFoundException;
import com.rightpair.jwt.JwtPair;
import com.rightpair.repository.MemberRepository;
import com.rightpair.security.JwtPrincipal;
import com.rightpair.service.AuthService;
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
