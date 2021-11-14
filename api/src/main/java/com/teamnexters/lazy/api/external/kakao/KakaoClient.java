package com.teamnexters.lazy.api.external.kakao;

import com.teamnexters.lazy.api.domain.oauth.kakao.KakaoProfileDto;
import com.teamnexters.lazy.api.exception.ExternalApiException;
import com.teamnexters.lazy.api.exception.TokenValidException;
import com.teamnexters.lazy.common.domain.member.Member;
import com.teamnexters.lazy.common.domain.member.Provider;
import com.teamnexters.lazy.common.domain.member.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class KakaoClient {

    private final WebClient webClient;

    // Kakao User 정보 받아오는 URI
    private static final String KAKAO_USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";

    public Member getUserInfoByKakaoApi(String accessToken) {
        KakaoProfileDto kakaoUserResponse = webClient.get()
                .uri(KAKAO_USER_INFO_URI)
                .headers(h -> h.setBearerAuth(accessToken)) // Bearer 설정
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenValidException()))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new ExternalApiException("GET : " + KAKAO_USER_INFO_URI)))
                .bodyToMono(KakaoProfileDto.class)
                .block();

        return Member.builder()
                .oauthId(String.valueOf(kakaoUserResponse.getId()))
                .name(kakaoUserResponse.getProperties().getNickname())
                .email(kakaoUserResponse.getAccount().getEmail())
                .provider(Provider.KAKAO)
                .role(Role.USER)
                .picture(kakaoUserResponse.getProperties().getProfileImage())
                .build();
    }
}
