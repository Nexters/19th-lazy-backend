package com.teamnexters.lazy.common.domain.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignUpReq {
        private String email;
        private String name;

        public SignUpReq(String email, String name) {
            this.email = email;
            this.name = name;
        }

        public Member toEntity() {
            return Member.builder()
                .email(this.email)
                .name(this.name)
                .build();
        }

    }

    @Getter
    public static class Res {
        private final String email;
        private final String name;

        public Res(Member member) {
            this.email = member.getEmail();
            this.name = member.getName();
        }

    }

}
