package com.rightpair.auth.security;

import com.rightpair.auth.service.AuthService;
import com.rightpair.auth.service.response.GetMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        GetMemberResponse memberResponse = authService.getMemberByEmail(email);
        return JwtPrincipal.from(
                memberResponse.id(),
                memberResponse.email(),
                memberResponse.name(),
                memberResponse.enabled(),
                memberResponse.roles());
    }
}
