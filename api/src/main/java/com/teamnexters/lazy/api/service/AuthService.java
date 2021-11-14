package com.teamnexters.lazy.api.service;

import com.teamnexters.lazy.api.config.auth.jwt.JwtTokenProvider;
import com.teamnexters.lazy.api.config.auth.jwt.Token;
import com.teamnexters.lazy.api.external.kakao.KakaoClient;
import com.teamnexters.lazy.common.domain.member.Member;
import com.teamnexters.lazy.common.domain.member.MemberRepository;
import com.teamnexters.lazy.common.domain.member.Provider;
import com.teamnexters.lazy.common.domain.member.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource("classpath:application-oauth.yml")
public class AuthService {

    @Value("${spring.security.oauth2.client.registration.kakao.auth-url}")
    private String kakaoAuthUrl; // Auth URL

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri; // Redirect URI

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoRestApiKey; // REST API Key

    @Value("${spring.security.oauth2.client.registration.kakao.scope}")
    private String kakaoScope; // 추가 동의 항목

    private final KakaoClient kakaoClient;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Kakao landing URL 생성
     * @return Kakao landing URL
     */
    public String makeKakaoLandingUrl() {
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

        return url.toString();
    }

    /**
     * 카카오 API 통해 회원 정보 가져와서 생성 후 Token 발행
     * 
     * @param kakaoToken Kakao Token
     * @return App Server Token
     */
    @Transactional
    public Token getTokenAndSaveMemberAfterKakaoUserApi(String kakaoToken) {
        // Kakao External Client API 호출
        Member kakaoUserInfo = kakaoClient.getUserInfoByKakaoApi(kakaoToken);
        String oauthId = kakaoUserInfo.getOauthId();
        // Member
        Member member = memberRepository.findByOauthIdAndProvider(oauthId, Provider.KAKAO).orElseGet(
                () -> memberRepository.save(kakaoUserInfo)
        );

        // Token 생성
        Token token = jwtTokenProvider.createToken(member.getEmail(), Role.USER);
        log.info("### Token : [ {} ]", token);
        return token;
    }

}
