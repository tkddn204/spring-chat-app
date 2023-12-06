package com.rightpair.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/v1/oauth")
@RequiredArgsConstructor
public class OpenAuthController {
    private final static String AUTH_REQUEST_BASE_URI = "/oauth2/authorization/";

    @GetMapping("/login/{provider}")
    public void requestOpenAuth(
            @PathVariable("provider") String provider,
            HttpServletResponse response
    ) throws IOException {
        response.sendRedirect(AUTH_REQUEST_BASE_URI + provider);
    }

    @GetMapping("/login/success")
    public String successOpenAuth() {
        return "Success";
    }
}
