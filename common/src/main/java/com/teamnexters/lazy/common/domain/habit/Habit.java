package com.teamnexters.lazy.common.domain.habit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teamnexters.lazy.common.domain.BaseTimeEntity;
import lombok.*;
import javax.persistence.*;
import java.time.LocalTime;

@Getter
@Table(name = "habit")
@ToString(of = {"habitName", "habitDetail"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Habit extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "habit_idx")
    private Long habitIdx;

    @Column(name = "mem_idx")
    private Long memIdx;

    @Column(name = "habit_name", nullable = false)
    private String habitName;

    @Column(name = "habit_detail")
    private String habitDetail;

    @Column(name = "habit_category", nullable = false)
    private Integer habitCategory;

    @Column(name = "habit_frequency")
    private String habitFrequency;

    @Column(name = "habit_delay_day", nullable = false)
    private Integer habitDelayDay;

    @Column(name = "habit_notice_state")
    private Boolean habitNoticeState;

    @JsonFormat(pattern = "HH:mm")
    @Column(name = "habit_notice_time")
    private LocalTime habitNoticeTime;

    @Builder
    public Habit(Long memIdx, String habitName, String habitDetail, Integer habitCategory,
                 String habitFrequency, Integer habitDelayDay, Boolean habitNoticeState, LocalTime habitNoticeTime) {
        this.memIdx = memIdx;
        this.habitName = habitName;
        this.habitDetail = habitDetail;
        this.habitCategory = habitCategory;
        this.habitFrequency = habitFrequency;
        this.habitDelayDay = habitDelayDay;
        this.habitNoticeState = habitNoticeState;
        this.habitNoticeTime = habitNoticeTime;
    }

    public Habit update(String habitName, String habitDetail, Integer habitCategory,
                        Boolean habitNoticeState, LocalTime habitNoticeTime) {
        this.habitName = habitName;
        this.habitDetail = habitDetail;
        this.habitCategory = habitCategory;
        this.habitNoticeState = habitNoticeState;
        this.habitNoticeTime = habitNoticeTime;

        return this;
    }

}
