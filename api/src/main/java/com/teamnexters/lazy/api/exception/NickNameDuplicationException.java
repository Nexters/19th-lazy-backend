package com.teamnexters.lazy.api.exception;

import com.teamnexters.lazy.common.error.ErrorCode;
import com.teamnexters.lazy.common.error.exception.BusinessException;
import lombok.Getter;

@Getter
public class NickNameDuplicationException extends BusinessException {

    public NickNameDuplicationException(String nickName) {
        super(String.format("NickName [%s] is Duplicated", nickName), ErrorCode.NICKNAME_DUPLICATION);
    }

}
