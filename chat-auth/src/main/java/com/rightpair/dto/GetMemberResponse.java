package com.rightpair.dto;

import com.rightpair.domain.member.Member;

import java.util.List;

public record GetMemberResponse(
        Member member,
        List<String> roles
) {
    public static GetMemberResponse create(Member member, List<String> roles) {
        return new GetMemberResponse(member, roles);
    }
}
