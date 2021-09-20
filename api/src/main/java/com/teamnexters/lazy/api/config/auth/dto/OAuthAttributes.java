package com.teamnexters.lazy.api.config.auth.dto;
import com.teamnexters.lazy.api.exception.OAuth2ProviderNotMatchException;
import com.teamnexters.lazy.common.domain.member.Member;
import com.teamnexters.lazy.common.domain.member.Provider;
import com.teamnexters.lazy.common.domain.member.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Builder(access = AccessLevel.PRIVATE)
@ToString
@Getter
public class OAuthAttributes {
    private final Map<String, Object> attributes; // OAuth2 반환하는 유저 정보 Map
    private final String nameAttributeKey;
    private final String name;
    private final String email;
    private final String picture;
    private final Provider provider;

    public static OAuthAttributes of(String provider, String userNameAttributeName, Map<String, Object> attributes){

        switch (provider) {
            case "google":
                return ofGoogle(userNameAttributeName, attributes);
            case "kakao":
                return ofKakao(attributes);
            case "naver":
                return ofNaver(attributes);
            default:
                throw new OAuth2ProviderNotMatchException(provider);
        }
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .provider(Provider.GOOGLE)
                .build();
    }

    private static OAuthAttributes ofKakao(Map<String, Object> attributes) {
        // JSON 형태를 Map 으로 가져오기 (kakao_account)
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        // Profile 조회
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .picture((String) kakaoProfile.get("profile_image_url"))
                .attributes(kakaoAccount)
                .nameAttributeKey("email")
                .provider(Provider.KAKAO)
                .build();
    }

    private static OAuthAttributes ofNaver(Map<String, Object> attributes) {
        // JSON 형태를 Map 으로 가져오기
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
            .name((String) response.get("name"))
            .email((String) response.get("email"))
            .picture((String) response.get("profile_image"))
            .attributes(response)
            .nameAttributeKey("id")
            .provider(Provider.NAVER)
            .build();
    }

    public Member toEntity(){
        return Member.builder()
            .name(name)
            .email(email)
            .picture(picture)
            .role(Role.USER) // 기본 권한 GUEST, 일반 : USER
            .provider(provider)
            .build();
    }

}