package com.teamnexters.lazy.api.domain;

import com.teamnexters.lazy.common.domain.habit.Habit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class HabitDto {

    /**
     * 습관 추가하는 Request DTO
     */
    @Schema(description = "습관 추가 요청 파라미터")
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AddHabitReq {

        @Schema(description = "습관 이름", defaultValue = "사이드레터럴레이즈 5세트")
        private String habitName;
        @Schema(description = "습관 설명", defaultValue = "1세트 30개, 휴식 30초")
        private String habitDetail;
        @Schema(description = "습관 카테고리", defaultValue = "143")
        private Integer habitCategory;
        @Schema(description = "습관 주기", defaultValue = "1,2,3")
        private String habitFrequency;
        @Schema(description = "알림 여부", defaultValue = "true")
        private Boolean noticeState;
        @Schema(description = "알림 시간", defaultValue = "12:00")
        private String noticeTime;

        public AddHabitReq(String habitName, String habitDetail, Integer habitCategory,
                           String habitFrequency, Boolean noticeState, String noticeTime) {
            this.habitName = habitName;
            this.habitDetail = habitDetail;
            this.habitCategory = habitCategory;
            this.habitFrequency = habitFrequency;
            this.noticeState = noticeState;
            this.noticeTime = noticeTime;
        }

        public Habit toEntity() {
            return Habit.builder()
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
     * 습관 업데이트 요청 Dto
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Schema(description = "Request to change Habit Info.")
    public static class UpdateHabitReq {
        @Schema(description = "습관 index", defaultValue = "1")
        private Long habitIdx;
        @Schema(description = "습관 이름", defaultValue = "변경할 습관")
        private String habitName;
        @Schema(description = "습관 설명", defaultValue = "설명은 사실 필요가 없었음")
        private String habitDetail;
        @Schema(description = "습관 카테고리", defaultValue = "122")
        private Integer habitCategory;
        @Schema(description = "습관 주기", defaultValue = "4,5")
        private String habitFrequency;
        @Schema(description = "알림 여부", defaultValue = "true")
        private Boolean noticeState;
        @Schema(description = "알림 시간", defaultValue = "15:00")
        private String noticeTime;
    }

    /**
     * 습관 추가/수정 완료 응답
     */
    @Getter
    @AllArgsConstructor
    @Schema(description = "습관 추가/수정 완료 응답")
    public static class HabitRes {
        @Schema(description = "습관 index", defaultValue = "1")
        private final Long habitIdx;
        @Schema(description = "습관 이름", defaultValue = "사이드레터럴레이즈 5세트/변경한 습관 이름")
        private final String habitName;
    }

    /**
     * 습관 전체 보기 응답
     */
    @Getter
    @AllArgsConstructor
    @Schema(description = "습관 데이터")
    public static class AllHabitRes {
        @Schema(description = "습관 번호", defaultValue = "1")
        private final Long habitIdx;
        @Schema(description = "습관 이름", defaultValue = "사이드레터럴레이즈 5세트")
        private final String habitName;
        @Schema(description = "회원 번호", defaultValue = "2")
        private final Long memIdx;
        @Schema(description = "습관 설명", defaultValue = "1세트 30개, 휴식 30초")
        private final String habitDetail;
        @Schema(description = "습관 카테고리", defaultValue = "143")
        private final Integer habitCategory;
        @Schema(description = "습관 주기", defaultValue = "1,2,3")
        private final String habitFrequency;
        @Schema(description = "알림 여부", defaultValue = "true")
        private final Boolean noticeState;
        @Schema(description = "알림 시간", defaultValue = "12:00")
        private final String noticeTime;
        @Schema(description = "생성 시간", defaultValue = "2021-09-25 18:31:52")
        private final LocalDateTime createdDt;
        @Schema(description = "수정 시간", defaultValue = "2021-09-25 18:31:52")
        private final LocalDateTime modifiedDt;
    }

    /**
     * 습관 번호 응답 Dto
     */
    @Getter
    @AllArgsConstructor
    @Schema(description = "Habit Index")
    public static class HabitIndexRes {
        @Schema(description = "습관 번호", defaultValue = "3")
        private final Long habitIdx;
    }
}
