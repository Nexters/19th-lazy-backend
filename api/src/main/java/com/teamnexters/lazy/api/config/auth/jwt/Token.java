package com.teamnexters.lazy.api.config.auth.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class Token {
    private String accessToken;
    private String refreshToken;

    public Token(String token, String refreshToken) {
        this.accessToken = token;
        this.refreshToken = refreshToken;
    }
}