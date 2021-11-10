package com.teamnexters.lazy.api.domain.oauth.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoTokenDto {

    // 사용자 액세스 토큰 값
    private String accessToken;
    // 엑세스 토큰 만료 시간(초)
    private long expiresIn;
    // 토큰 타입 = bearer 고정
    private String tokenType;
    // 사용자 리프레시 토큰 값
    private String refreshToken;
    // 리프레시 토큰 만료 시간(초)
    private String refreshTokenExpiresIn;
    // 인증된 사용자의 정보 조회 권한 범위(필수 아님)
    private String scope;
}
