package com.rightpair.dto;

import com.rightpair.domain.member.Member;

import java.util.List;

public record GetMemberResponse(
        String id,
        String email,
        String name,
        boolean enabled,
        List<String> roles
) {
    public static GetMemberResponse create(Member member, List<String> roles) {
        return new GetMemberResponse(
                String.valueOf(member.getId()),
                member.getEmail(),
                member.getName(),
                member.getEnabled(),
                roles
        );
    }
}
