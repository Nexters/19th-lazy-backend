package com.teamnexters.lazy.api.exception;

import com.teamnexters.lazy.common.error.ErrorCode;
import com.teamnexters.lazy.common.error.exception.BusinessException;
import lombok.Getter;

@Getter
public class HabitDuplicationException extends BusinessException {

    public HabitDuplicationException(String habitName) {
        super(String.format("Habit [%s] is Duplicated", habitName), ErrorCode.HABIT_DUPLICATION);
    }

}
