package com.rightpair.domain.Member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAuthority {

    @EmbeddedId
    private MemberAuthorityId memberAuthorityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authority_id", insertable = false, updatable = false)
    private Authority authority;

    private MemberAuthority(Member member, Authority authority) {
        this.memberAuthorityId = MemberAuthorityId.create(member.getId(), authority.getId());
        this.member = member;
        this.authority = authority;
    }

    public static MemberAuthority create(Member member, Authority authority) {
        return new MemberAuthority(member, authority);
    }
}
