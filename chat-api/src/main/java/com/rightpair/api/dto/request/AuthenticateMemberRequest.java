package com.rightpair.api.dto.request;

public record AuthenticateMemberRequest (
        String email,
        String password
) {
    public static AuthenticateMemberRequest from(AuthenticateMemberControllerRequest controllerRequest) {
        return new AuthenticateMemberRequest(controllerRequest.email(), controllerRequest.password());
    }
}
