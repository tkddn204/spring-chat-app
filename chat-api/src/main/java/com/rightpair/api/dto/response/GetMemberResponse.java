package com.rightpair.api.dto.response;

import com.rightpair.core.domain.member.Member;

import java.util.List;

public record GetMemberResponse(
        String id,
        String email,
        String name,
        boolean enabled,
        List<String> roles
) {
    public static GetMemberResponse create(Member member) {
        return new GetMemberResponse(
                String.valueOf(member.getId()),
                member.getEmail(),
                member.getName(),
                member.getEnabled(),
                member.getRolesToStrList()
        );
    }
}
