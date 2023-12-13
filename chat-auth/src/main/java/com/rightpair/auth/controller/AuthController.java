package com.rightpair.auth.controller;

import com.rightpair.auth.controller.request.AuthenticateMemberControllerRequest;
import com.rightpair.auth.controller.request.RefreshAccessTokenControllerRequest;
import com.rightpair.auth.controller.request.RegisterMemberControllerRequest;
import com.rightpair.auth.dto.*;
import com.rightpair.auth.resolver.AuthContext;
import com.rightpair.auth.security.JwtPrincipal;
import com.rightpair.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticateMemberResponse> authenticateMember(
            @Valid @RequestBody AuthenticateMemberControllerRequest controllerRequest
    ) {
        return ResponseEntity.ok(
                authService.authenticateMember(AuthenticateMemberRequest.from(controllerRequest))
        );
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterMemberResponse> registerMember(
            @Valid @RequestBody RegisterMemberControllerRequest controllerRequest
    ) {
        return ResponseEntity.created(URI.create("/")).body(
                authService.registerMember(RegisterMemberRequest.from(controllerRequest))
        );
    }

    @PostMapping("/signout")
    public ResponseEntity<Void> signOutMember(
            @AuthContext JwtPrincipal jwtPrincipal
    ) {
        authService.unAuthenticateMember(Long.valueOf(jwtPrincipal.getMemberId()));
        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping("/info")
    public ResponseEntity<GetMemberResponse> infoMember(
            @AuthContext JwtPrincipal jwtPrincipal
    ) {
        return ResponseEntity.ok(
                authService.getMemberByEmail(jwtPrincipal.getMemberEmail())
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticateMemberResponse> refreshAccessToken(
            @Valid @RequestBody RefreshAccessTokenControllerRequest controllerRequest
    ) {
        return ResponseEntity.ok(
                authService.refreshAccessToken(controllerRequest.refreshToken())
        );
    }
}
