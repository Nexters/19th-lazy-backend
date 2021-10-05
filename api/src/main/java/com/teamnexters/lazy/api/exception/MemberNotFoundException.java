package com.teamnexters.lazy.api.exception;

import com.teamnexters.lazy.common.error.ErrorCode;
import com.teamnexters.lazy.common.error.exception.BusinessException;
import lombok.Getter;

@Getter
public class MemberNotFoundException extends BusinessException {

    public MemberNotFoundException(String memberIdx) {
        super(String.format("Member Index [%s] not Found", memberIdx), ErrorCode.MEMBER_NOT_FOUND);
    }

}
