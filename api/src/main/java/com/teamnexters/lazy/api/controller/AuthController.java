package com.teamnexters.lazy.api.controller;

import com.teamnexters.lazy.api.config.auth.jwt.Token;
import com.teamnexters.lazy.api.service.AuthService;
import com.teamnexters.lazy.common.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Auth Controller", description = "소셜 로그인 관련 컨트롤러")
@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "✅ 카카오 OAuth 랜딩 API",
            description = "카카오 OAuth 페이지를 랜딩해요.",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "[Ok] Kakao Login Page Landing")})
    @GetMapping("api/v1/oauth/kakao")
    public String kakaoLoginLanding() {
        String kakaoLandingUrl = authService.makeKakaoLandingUrl();
        return "redirect:" + kakaoLandingUrl;
    }

    @Operation(summary = "✅ 카카오 액세스 토큰으로 App JWT 생성",
            description = "카카오 액세스 토큰으로 App Server JWT 얻어와요.",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "[Ok] JWT Create By Kakao Token",
                            content = @Content(schema = @Schema(implementation = Token.class))),
                    @ApiResponse(
                            responseCode = "408", description = "[Error] Token is not valid",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping(value = "api/v1/token/{kakao-token}")
    public ResponseEntity<Token> kakaoAuthRequest(@PathVariable(name="kakao-token") String kakaoToken) {
        return ResponseEntity.ok().body(authService.getTokenAndSaveMemberAfterKakaoUserApi(kakaoToken));
    }

}
