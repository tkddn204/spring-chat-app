package com.rightpair.core.domain.member;


import com.rightpair.core.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<MemberRole> roles;

    @Column(nullable = false)
    private Boolean enabled;

    public Member(String email, String password, String name, List<MemberRole> roles, Boolean enabled) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles = roles;
        this.enabled = enabled;
    }

    public static Member create(String email, String password, String name) {
        return new Member(email, password, name, Collections.emptyList(), true);
    }

    public List<String> getRolesToStrList() {
        if (this.roles == null) {
            return Collections.emptyList();
        }
        return this.roles.stream().map(memberRole -> memberRole.getRole().getRoleType().name()).toList();
    }
}
