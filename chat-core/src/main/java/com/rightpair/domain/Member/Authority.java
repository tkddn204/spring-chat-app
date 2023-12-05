package com.rightpair.domain.Member;

import com.rightpair.type.AuthorityType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthorityType authority;

    private Authority(AuthorityType authority) {
        this.authority = authority;
    }

    public static Authority create(AuthorityType authority) {
        return new Authority(authority);
    }
}
