package com.teamnexters.lazy.api.domain;

import com.teamnexters.lazy.common.domain.habit.Habit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;


public class HabitDto {

    /**
     * 습관 추가하는 Request DTO
     */
    @Schema(description = "습관 추가 요청 파라미터")
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AddHabitReq {

        @Schema(description = "회원 번호", defaultValue = "2")
        private Long memIdx;
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

        // 알림 시간
        @Schema(hidden = true)
        private LocalTime finalNoticeTime;

        public AddHabitReq(Long memIdx, String habitName, String habitDetail, Integer habitCategory,
                           String habitFrequency, Boolean noticeState, String noticeTime) {
            this.memIdx = memIdx;
            this.habitName = habitName;
            this.habitDetail = habitDetail;
            this.habitCategory = habitCategory;
            this.habitFrequency = habitFrequency;
            this.noticeState = noticeState;
            List<String> hourAndMin = Arrays.asList(noticeTime.split(":"));
            LocalTime formTime = LocalTime.of(Integer.parseInt(hourAndMin.get(0)),
                    Integer.parseInt(hourAndMin.get(1)));
            this.finalNoticeTime = formTime;
        }

        public Habit toEntity() {
            return Habit.builder()
                    .memIdx(this.memIdx)
                    .habitName(this.habitName)
                    .habitDetail(this.habitDetail)
                    .habitCategory(this.habitCategory)
                    .habitNoticeState(this.noticeState)
                    .habitNoticeTime(this.finalNoticeTime)
                    .habitDelayDay(0)
                    .habitFrequency(this.habitFrequency)
                    .build();
        }
    }

    /**
     * 습관 추가 완료 응답
     */
    @Getter
    @AllArgsConstructor
    @Schema(description = "습관 추가 완료 응답")
    public static class HabitRes {
        @Schema(description = "습관 index", defaultValue = "1")
        private final Long habitIdx;
        @Schema(description = "습관 이름", defaultValue = "사이드레터럴레이즈 5세트")
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
