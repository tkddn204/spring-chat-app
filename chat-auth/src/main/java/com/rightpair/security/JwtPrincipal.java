package com.rightpair.security;

import com.rightpair.domain.member.Member;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@ToString
@EqualsAndHashCode(callSuper = false)
public class JwtPrincipal implements UserDetails {
    private final String memberId;
    private final String memberEmail;
    private final boolean enabled;
    private final List<GrantedAuthority> authorities;

    public JwtPrincipal(String memberId, String memberEmail,
                        boolean enabled, List<GrantedAuthority> authorities) {
        this.memberId = memberId;
        this.memberEmail = memberEmail;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public static JwtPrincipal create(Member member, List<String> roles) {
        return new JwtPrincipal(
                String.valueOf(member.getId()),
                member.getEmail(),
                member.getEnabled(),
                AuthorityUtils.createAuthorityList(roles)
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.memberEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
