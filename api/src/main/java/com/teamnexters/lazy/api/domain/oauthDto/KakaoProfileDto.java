package com.teamnexters.lazy.api.domain.oauthDto;

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

    @Getter
    @Setter
    @ToString
    private static class Properties {
        @JsonProperty("nickname")
        private String nickname;
        @JsonProperty("thumbnail_image")
        private String thumbnailImage;
        @JsonProperty("profile_image")
        private String profileImage;
    }
}