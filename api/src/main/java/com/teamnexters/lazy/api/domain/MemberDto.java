package com.teamnexters.lazy.api.domain;

import com.teamnexters.lazy.common.domain.member.Provider;
import com.teamnexters.lazy.common.domain.member.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class MemberDto {

    /**
     * 업데이트 요청 Dto
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Schema(description = "Request to change NickName")
    public static class UpdateNickNameReq {
        @Schema(description = "회원 닉네임", defaultValue = "변경할 닉네임")
        private String nickName;
    }

    /**
     * 중복 체크할 닉네임 Dto
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Schema(description = "Request to check duplication NickName")
    public static class checkNickNameReq {
        @Schema(description = "회원 닉네임", defaultValue = "체크할 닉네임")
        private String nickName;
    }

    /**
     * 회원 데이터 Dto
     */
    @Getter
    @AllArgsConstructor
    @Schema(description = "Member Data")
    public static class AllMemberRes {
        @Schema(description = "회원 번호", defaultValue = "2")
        private final Long memIdx;
        @Schema(description = "회원 이름", defaultValue = "박영준")
        private final String name;
        @Schema(description = "회원 닉네임", defaultValue = "밍굴")
        private final String nickName;
        @Schema(description = "회원 이메일", defaultValue = "youngjun108059@gmail.com")
        private final String email;
        @Schema(description = "OAuth 프로필 사진", defaultValue = "")
        private final String picture;
        @Schema(description = "습관 index", defaultValue = "1")
        private final Role role;
        @Schema(description = "습관 index", defaultValue = "1")
        private final Provider provider;
        @Schema(description = "생성 시간", defaultValue = "2021-09-25 18:31:52")
        private final LocalDateTime createdDt;
        @Schema(description = "수정 시간", defaultValue = "2021-09-25 18:31:52")
        private final LocalDateTime modifiedDt;
    }

    /**
     * 회원 번호 응답 Dto
     */
    @Getter
    @AllArgsConstructor
    @Schema(description = "Member Index")
    public static class MemberIndexRes {
        @Schema(description = "회원 번호", defaultValue = "2")
        private final Long memIdx;
    }

}
