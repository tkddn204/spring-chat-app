package com.rightpair.api.service.request;

import com.rightpair.api.controller.request.AuthenticateMemberControllerRequest;

public record AuthenticateMemberRequest (
        String email,
        String password
) {
    public static AuthenticateMemberRequest from(AuthenticateMemberControllerRequest controllerRequest) {
        return new AuthenticateMemberRequest(controllerRequest.email(), controllerRequest.password());
    }
}
