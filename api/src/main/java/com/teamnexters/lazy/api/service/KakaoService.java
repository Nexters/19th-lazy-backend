package com.teamnexters.lazy.api.service;

import com.google.gson.Gson;
import com.teamnexters.lazy.api.domain.oauthDto.KakaoProfileDto;
import com.teamnexters.lazy.api.domain.oauthDto.KakaoTokenDto;
import com.teamnexters.lazy.api.exception.ExternalApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@PropertySource("classpath:application-oauth.yml")
@RequiredArgsConstructor
@Service
public class KakaoService {

    private final RestTemplate restTemplate;
    private final Gson gson;

    // 개발, 운영 환경 base-url
    @Value("${spring.base-url}")
    private String baseUrl;

    // 카카오 API Key
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoApiKey;

    // 카카오 Redirect URL
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    // 카카오 회원 조회 URL
    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String profileUri;

    // 카카오 토큰 조회 URL
    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String tokenUri;

    /**
     * 토큰으로 카카오 프로필 조회
     *
     * @param accessToken AccessToken
     * @return 카카오 프로필
     */
    public KakaoProfileDto getKakaoProfile(String accessToken) {
        // Set header : Content-type: application/x-www-form-urlencoded
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);

        // Set http entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        try {
            // Request profile
            ResponseEntity<String> response = restTemplate.postForEntity(profileUri, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK)
                return gson.fromJson(response.getBody(), KakaoProfileDto.class);
        } catch (Exception e) {
            throw new ExternalApiException("Kakao API");
        }
        throw new ExternalApiException("Kakao API");
    }

    /**
     * 코드로 토큰 정보 받아오기
     *
     * @param code 카카오 코드
     * @return 카카오 토큰 정보
     */
    public KakaoTokenDto getKakaoTokenInfo(String code) {

        // Set parameter (Post 보내기 위한 Body)
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoApiKey);
        params.add("redirect_uri", baseUrl + redirectUri);
        params.add("code", code);

        // Set header : Content-type: application/x-www-form-urlencoded
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Set http entity (MultiValueMap 타입으로 JSON 형식 만들어주기)
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUri, request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return gson.fromJson(response.getBody(), KakaoTokenDto.class);
        }
        return null;
    }
}