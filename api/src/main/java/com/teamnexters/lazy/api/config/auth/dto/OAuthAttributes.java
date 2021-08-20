package com.teamnexters.lazy.api.config.auth.dto;
import com.teamnexters.lazy.common.domain.member.Member;
import com.teamnexters.lazy.common.domain.member.Provider;
import com.teamnexters.lazy.common.domain.member.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private final Map<String, Object> attributes; // OAuth2 반환하는 유저 정보 Map
    private final String nameAttributeKey;
    private final String name;
    private final String email;
    private final String picture;
    private final Provider provider;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture, Provider provider) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.provider = provider;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        // Naver 로그인
        if (registrationId.equals("naver")) {
            return ofNaver(attributes);
        }

        // Kakao 로그인
        if (registrationId.equals("kakao")) {
            return ofKakao(attributes);
        }

        // Google 로그인
        return ofGoogle(userNameAttributeName, attributes);
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

    private static OAuthAttributes ofKakao(Map<String, Object> attributes) {
        // JSON 형태를 Map 으로 가져오기 (kakao_account)
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        // Profile 조회
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuthAttributes.builder()
            .name((String) kakaoProfile.get("nickname"))
            .email((String) kakaoProfile.get("email"))
            .picture((String) kakaoProfile.get("profile_image_url"))
            .attributes(attributes)
            .nameAttributeKey("id")
            .provider(Provider.KAKAO)
            .build();
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

    public Member toEntity(){
        return Member.builder()
            .name(name)
            .email(email)
            .picture(picture)
            .role(Role.GUEST) // 기본 권한 GUEST
            .provider(provider)
            .build();
    }

}