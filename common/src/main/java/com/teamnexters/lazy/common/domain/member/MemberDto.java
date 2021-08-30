package com.teamnexters.lazy.common.domain.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberDto {

    /**
     * 닉네임 변경 Request
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateNickNameReq {
        private Long memIdx;
        private String nickName;

        public UpdateNickNameReq(Long memIdx, String nickName) {
            this.memIdx = memIdx;
            this.nickName = nickName;
        }
    }

}
