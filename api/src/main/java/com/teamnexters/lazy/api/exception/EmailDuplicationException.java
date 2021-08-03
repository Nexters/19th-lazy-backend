package com.teamnexters.lazy.api.exception;

import com.teamnexters.lazy.common.error.ErrorCode;
import com.teamnexters.lazy.common.error.exception.BusinessException;
import lombok.Getter;

@Getter
public class EmailDuplicationException extends BusinessException {

    public EmailDuplicationException(String email) {
        super(String.format("Email [%s] is Duplicated", email), ErrorCode.EMAIL_DUPLICATION);
    }

}
