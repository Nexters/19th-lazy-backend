package com.teamnexters.lazy.api.domain;

import com.teamnexters.lazy.common.domain.habit.Habit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;


public class HabitDto {

    /**
     * 습관 추가하는 Request DTO
     */
    @Schema(description = "습관 추가 요청 파라미터")
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AddReq {
        @Schema(description = "회원 번호", defaultValue = "2")
        private Long memIdx;
        @Schema(description = "습관 이름", defaultValue = "사이드레터럴레이즈 5세트")
        private String habitName;
        @Schema(description = "습관 이름", defaultValue = "1세트 30개, 휴식 30초")
        private String habitDetail;
        @Schema(description = "습관 ", defaultValue = "사이드레터럴레이즈 5세트")
        private Integer habitCategory;
        private String habitFrequency;
        private Boolean noticeState;
        private LocalTime noticeTime;

        public AddReq(Long memIdx, String habitName, String habitDetail, Integer habitCategory,
                      String habitFrequency, Boolean noticeState, LocalTime noticeTime) {
            this.memIdx = memIdx;
            this.habitName = habitName;
            this.habitDetail = habitDetail;
            this.habitCategory = habitCategory;
            this.habitFrequency = habitFrequency;
            this.noticeState = noticeState;
            this.noticeTime = noticeTime;
        }

        public Habit toEntity() {
            return Habit.builder()
                    .memIdx(this.memIdx)
                    .habitName(this.habitName)
                    .habitDetail(this.habitDetail)
                    .habitCategory(this.habitCategory)
                    .habitNoticeState(this.noticeState)
                    .habitNoticeTime(this.noticeTime)
                    .habitDelayDay(0)
                    .habitFrequency(this.habitFrequency)
                    .build();
        }
    }

    /**
     * 습관 추가 완료 응답
     */
    @Getter
    public static class Res {
        private final Long habitIdx;
        private final String habitName;

        public Res(Long habitIdx, String habitName) {
            this.habitIdx = habitIdx;
            this.habitName = habitName;
        }

    }


    @Getter
    public static class GetAllRes {
        private final String habitName;
        private final String habitDetail;
        private final Integer habitCategory;
        private final Integer habitFrequency;
        private final Integer habitDelayDay;

        public GetAllRes(String habitName, String habitDetail, Integer habitCategory, Integer habitFrequency, Integer habitDelayDay) {
            this.habitName = habitName;
            this.habitDetail = habitDetail;
            this.habitCategory = habitCategory;
            this.habitFrequency = habitFrequency;
            this.habitDelayDay = habitDelayDay;
        }

    }

}
