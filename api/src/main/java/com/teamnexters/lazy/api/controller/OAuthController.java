package com.teamnexters.lazy.api.controller;

import com.teamnexters.lazy.api.config.auth.jwt.JwtTokenProvider;
import com.teamnexters.lazy.api.config.auth.jwt.Token;
import com.teamnexters.lazy.api.domain.oauthDto.KakaoProfileDto;
import com.teamnexters.lazy.api.domain.oauthDto.KakaoTokenDto;
import com.teamnexters.lazy.api.exception.MemberNotFoundException;
import com.teamnexters.lazy.api.service.KakaoService;
import com.teamnexters.lazy.common.domain.member.Member;
import com.teamnexters.lazy.common.domain.member.MemberRepository;
import com.teamnexters.lazy.common.domain.member.Provider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "OAuth 소셜로그인", description = "소셜로그인 관련 API")
@RestController
@AllArgsConstructor
public class OAuthController {

    private KakaoService kakaoService;
    private MemberRepository memberRepository;
    private JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "✅ Kakao Callback - AccessToken 받아오기",
            description = "카카오 로그인 후 코드로 AccessToken 을 받아옵니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "[Ok] GET AccessToken",
                            content = @Content(schema = @Schema(implementation = String.class)))})
    @GetMapping(value = "/login/oauth2/code/kakao")
    public String kakaoLogin(@RequestParam("code") String code) {
        KakaoTokenDto kakaoToken = kakaoService.getKakaoTokenInfo(code);
        log.info(">>> Callback : Kakao Token = {}", kakaoToken);
        return kakaoToken.getAccessToken();
    }

    @Operation(summary = "✅ 소셜로그인 요청 API",
            description = "소셜로그인 토큰으로 밍굴맹굴 로그인을 시도합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "[Ok] Social Login complete",
                            content = @Content(schema = @Schema(implementation = Token.class)))})
    @GetMapping(value = "api/v1/oauth/sign-in/kakao")
    public ResponseEntity<Token> signInByProvider(
            @Parameter(description = "소셜 Access Token", required = true) @RequestBody String accessToken) {

        KakaoProfileDto profile = kakaoService.getKakaoProfile(accessToken);

        Integer oauthId = Math.toIntExact(profile.getId());
        Member member = memberRepository.findByOauthIdAndProvider(oauthId, Provider.KAKAO)
                .orElseThrow(() -> new MemberNotFoundException(String.valueOf(oauthId)));
        return ResponseEntity.ok().body(jwtTokenProvider.createToken(String.valueOf(member.getMemIdx()), member.getRole()));
    }


}
