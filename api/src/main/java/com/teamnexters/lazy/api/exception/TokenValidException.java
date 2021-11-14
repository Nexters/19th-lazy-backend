package com.teamnexters.lazy.api.exception;

import com.teamnexters.lazy.common.error.ErrorCode;
import com.teamnexters.lazy.common.error.exception.BusinessException;

public class TokenValidException extends BusinessException {
    public TokenValidException() {
        super(String.format("Token is not valid."), ErrorCode.TOKEN_IS_NOT_VALID);
    }
}
