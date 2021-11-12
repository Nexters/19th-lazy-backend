package com.teamnexters.lazy.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Auth Controller", description = "소셜 로그인 관련 컨트롤러")
@Slf4j
@Controller
public class AuthController {

    @Value("${spring.security.oauth2.client.registration.kakao.auth-url}")
    private String kakaoAuthUrl; // Auth URL

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri; // Redirect URI

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoRestApiKey; // REST API Key

    @Value("${spring.security.oauth2.client.registration.kakao.scope}")
    private String kakaoScope; // 추가 동의 항목

    @Operation(summary = "✅ 카카오 OAuth 랜딩 API",
            description = "카카오 OAuth 페이지를 랜딩해요.",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "[Ok] Kakao Login Page Landing")})
    @GetMapping("api/v1/oauth/kakao")
    public String kakaoLoginLanding() {
        StringBuffer url = new StringBuffer();
        url.append(kakaoAuthUrl)
            .append("/oauth/authorize?")
            .append("client_id=")
            .append(kakaoRestApiKey)
            .append("&redirect_uri=")
            .append(kakaoRedirectUri)
            .append("&scope=")
            .append(kakaoScope)
            .append("&response_type=")
            .append("code"); // code 고정

        log.info("Kakao Login Landing URL : {}", url);

        return "redirect:" + url;
    }

}
