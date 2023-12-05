package com.rightpair.dto;

public record AuthenticateMemberRequest (
        String email,
        String password
) {
}
