package com.rightpair.dto;

import com.rightpair.domain.member.Member;

public record OAuthRegisterMemberResponse(
        Member member
) {
    public static OAuthRegisterMemberResponse from(Member member) {
        return new OAuthRegisterMemberResponse(member);
    }
}
