package com.rightpair.core.domain.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRole {

    @EmbeddedId
    private MemberRoleId memberRoleId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @MapsId("memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @MapsId("roleId")
    private Role role;

    private MemberRole(Member member, Role role) {
        this.memberRoleId = MemberRoleId.create(member.getId(), role.getId());
        this.member = member;
        this.role = role;
    }

    public static MemberRole create(Member member, Role role) {
        return new MemberRole(member, role);
    }
}
