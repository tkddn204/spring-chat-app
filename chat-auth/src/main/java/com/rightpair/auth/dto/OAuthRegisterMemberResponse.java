package com.rightpair.auth.dto;

import com.rightpair.core.domain.member.Member;

public record OAuthRegisterMemberResponse(
        Member member
) {
    public static OAuthRegisterMemberResponse from(Member member) {
        return new OAuthRegisterMemberResponse(member);
    }
}
