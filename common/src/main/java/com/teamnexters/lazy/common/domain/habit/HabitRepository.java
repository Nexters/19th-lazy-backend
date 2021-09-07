package com.teamnexters.lazy.common.domain.habit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HabitRepository extends JpaRepository<Habit, Long> {

    /**
     * 이미 등록된 습관인지
     *
     * @param memIdx     회원 번호
     * @param habitName  습관 이름
     * @return 존재하는 습관 반환
     */
    Optional<Habit> findByMemIdxAndHabitName(Long memIdx, String habitName);

    /**
     * 이미 등록된 습관인지 (idx 기준)
     *
     * @param memIdx    회원 번호
     * @param habitIdx  습관 번호
     * @return 존재하는 습관 반환
     */
    Optional<Habit> findByMemIdxAndHabitIdx(Long memIdx, Long habitIdx);

    /**
     * 회원의 습관 조회 (페이징)
     *
     * @param memIdx    회원 번호
     * @param pageable  페이징
     * @return 페이징된 습관
     */
    Page<Habit> findByMemIdx(Long memIdx, Pageable pageable);
}