package com.rightpair.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class JwtPrincipal implements UserDetails, OAuth2User {
    private final String memberId;
    private final String memberEmail;
    private final String memberName;
    private final boolean enabled;
    private final List<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public JwtPrincipal(String memberId, String memberEmail, String memberName,
                        boolean enabled, List<GrantedAuthority> authorities) {
        this.memberId = memberId;
        this.memberEmail = memberEmail;
        this.memberName = memberName;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public JwtPrincipal(String memberId, String memberEmail, String memberName,
                        boolean enabled, List<GrantedAuthority> authorities,
                        Map<String, Object> attributes
    ) {
        this.memberId = memberId;
        this.memberEmail = memberEmail;
        this.memberName = memberName;
        this.enabled = enabled;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    public static JwtPrincipal from(String memberId, String memberEmail, String memberName, boolean enabled, List<String> roles) {
        return new JwtPrincipal(memberId, memberEmail, memberName, enabled, AuthorityUtils.createAuthorityList(roles));
    }

    public static JwtPrincipal from(String memberId, String memberEmail, String memberName, boolean enabled,
                                    List<String> roles, Map<String, Object> attributes) {
        return new JwtPrincipal(memberId, memberEmail, memberName, enabled, AuthorityUtils.createAuthorityList(roles), attributes);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
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

    @Override
    public String getName() {
        return (String) attributes.getOrDefault("name", this.memberEmail);
    }
}
