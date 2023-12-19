package com.rightpair.api.service.request;

import com.rightpair.api.controller.request.RegisterMemberControllerRequest;

public record RegisterMemberRequest(
        String email,
        String password,
        String name
) {
    public static RegisterMemberRequest from(RegisterMemberControllerRequest controllerRequest) {
        return new RegisterMemberRequest(controllerRequest.email(), controllerRequest.password(), controllerRequest.name());
    }
}
