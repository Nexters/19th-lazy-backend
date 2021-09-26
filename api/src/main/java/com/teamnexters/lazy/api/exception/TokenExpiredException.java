package com.teamnexters.lazy.api.exception;

import com.teamnexters.lazy.common.error.ErrorCode;
import com.teamnexters.lazy.common.error.exception.BusinessException;

public class TokenExpiredException extends BusinessException {

    public TokenExpiredException() {
        super(String.format("Token is Expired"), ErrorCode.TOKEN_IS_EXPIRED);
    }

}
