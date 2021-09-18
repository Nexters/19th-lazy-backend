package com.teamnexters.lazy.api.service;

import com.teamnexters.lazy.api.domain.HabitDto;
import com.teamnexters.lazy.api.exception.HabitDuplicationException;
import com.teamnexters.lazy.common.domain.habit.Habit;
import com.teamnexters.lazy.common.domain.habit.HabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HabitService {

    private final HabitRepository habitRepository;

    /**
     * 습관 생성하기
     *
     * @param dto 습관 생성을 위한 DTO
     * @return 습관 생성 응답
     */
    @Transactional
    public HabitDto.Res create(HabitDto.AddReq dto) {
        // 습관 중복 검사
        if (isExistedHabit(dto.getMemIdx(), dto.getHabitName()))
            throw new HabitDuplicationException(dto.getHabitName());

        Habit entity = habitRepository.save(dto.toEntity());

        return new HabitDto.Res(entity.getHabitIdx(), entity.getHabitName());

    }

    /**
     * 페이징 적용한 습관 조회
     *
     * @param memIdx    회원 번호
     * @param pageable  페이징 정보
     * @return  습관 목록 조회
     */
    @Transactional(readOnly = true)
    public Page<Habit> getHabitList(Long memIdx, Pageable pageable) {
        return habitRepository.findByMemIdx(memIdx, pageable);
    }

    /**
     * 기존에 존재하는 습관인지
     *
     * @param memIdx    회원번호
     * @param habitName 습관이름
     * @return True : 이미 있음
     */
    @Transactional(readOnly = true)
    public boolean isExistedHabit(Long memIdx, String habitName) {
        return habitRepository.findByMemIdxAndHabitName(memIdx, habitName).orElseGet(null) != null;
    }

    /**
     * 습관을 삭제
     *
     * @param memIdx    회원번호
     * @param habitIdx  습관번호
     * @return 회원번호
     */
    @Transactional
    public Long deleteHabit(Long memIdx, Long habitIdx) {
        // 없은 습관인 경우 Exception Handling
        Habit habit = getOneHabit(memIdx, habitIdx);
        habitRepository.delete(habit);
        return memIdx;
    }

    /**
     * 습관 1개 조회
     *
     * @param memIdx    회원 번호
     * @param habitIdx  습관 번호
     * @return 해당 습관
     */
    @Transactional(readOnly = true)
    public Habit getOneHabit(Long memIdx, Long habitIdx) {
        return habitRepository.findByMemIdxAndHabitIdx(memIdx, habitIdx).orElseThrow();
    }



}
