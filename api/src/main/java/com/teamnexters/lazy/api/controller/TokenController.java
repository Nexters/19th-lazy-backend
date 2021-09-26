package com.teamnexters.lazy.api.controller;

import com.teamnexters.lazy.api.config.auth.jwt.JwtTokenProvider;
import com.teamnexters.lazy.api.config.auth.jwt.Token;
import com.teamnexters.lazy.api.exception.TokenExpiredException;
import com.teamnexters.lazy.common.domain.member.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class TokenController {

    private final JwtTokenProvider tokenProvider;

    @GetMapping("/token/expired")
    public String auth() {
        throw new TokenExpiredException();
    }

    @GetMapping("/token/refresh")
    public String refreshAuth(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Refresh");

        if (token != null && tokenProvider.verifyToken(token)) {
            String email = tokenProvider.getUid(token);
            Token newToken = tokenProvider.createToken(email, Role.USER);

            response.addHeader("Auth", newToken.getAccessToken());
            response.addHeader("Refresh", newToken.getRefreshToken());
            response.setContentType("application/json;charset=UTF-8");

            return "HAPPY NEW TOKEN";
        }

        throw new TokenExpiredException();
    }

}
