package com.rightpair.core.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRoleId implements Serializable {
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    private MemberRoleId(Long memberId, Long roleId) {
        this.memberId = memberId;
        this.roleId = roleId;
    }

    public static MemberRoleId create(Long memberId, Long roleId) {
        return new MemberRoleId(memberId, roleId);
    }
}
