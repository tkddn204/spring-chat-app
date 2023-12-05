package com.rightpair.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAuthorityId implements Serializable {
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "authority_id", nullable = false)
    private Long authorityId;

    private MemberAuthorityId(Long memberId, Long authorityId) {
        this.memberId = memberId;
        this.authorityId = authorityId;
    }

    public static MemberAuthorityId create(Long memberId, Long authorityId) {
        return new MemberAuthorityId(memberId, authorityId);
    }
}
