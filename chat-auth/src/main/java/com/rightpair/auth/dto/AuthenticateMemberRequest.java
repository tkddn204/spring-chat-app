package com.rightpair.auth.dto;

import com.rightpair.auth.controller.request.AuthenticateMemberControllerRequest;

public record AuthenticateMemberRequest (
        String email,
        String password
) {
    public static AuthenticateMemberRequest from(AuthenticateMemberControllerRequest controllerRequest) {
        return new AuthenticateMemberRequest(controllerRequest.email(), controllerRequest.password());
    }
}
