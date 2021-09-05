package com.teamnexters.lazy.common.domain.habit;

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

}