package com.teamnexters.lazy.api.domain.oauth.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoProfileDto {

    private Long id;
    private Properties properties;

    @JsonProperty("kakao_account")
    private Account account;

    @Getter
    @Setter
    @ToString
    public static class Properties {
        @JsonProperty("nickname")
        public String nickname;
        @JsonProperty("thumbnail_image")
        public String thumbnailImage;
        @JsonProperty("profile_image")
        public String profileImage;
    }

    /**
     * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
     */
    @Getter
    @Setter
    @ToString
    public static class Account {
        @JsonProperty("email")
        public String email;
    }
}