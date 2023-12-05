package com.rightpair.domain.Member;

import com.rightpair.type.RoleType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;

    private Role(RoleType roleType) {
        this.roleType = roleType;
    }

    public static Role create(RoleType roleType) {
        return new Role(roleType);
    }
}
