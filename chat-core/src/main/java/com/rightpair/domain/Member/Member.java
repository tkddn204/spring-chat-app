package com.rightpair.domain.Member;


import com.rightpair.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberAuthority> authorities;

    private Member(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static Member create(String email, String password, String name) {
        return new Member(email, password, name);
    }
}
