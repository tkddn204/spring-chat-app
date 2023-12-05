package com.rightpair.dto;

public record RegisterMemberRequest (
        String email,
        String password,
        String name
) {
}
