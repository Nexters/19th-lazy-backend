package com.teamnexters.lazy.api.exception;

import com.teamnexters.lazy.common.error.ErrorCode;
import com.teamnexters.lazy.common.error.exception.BusinessException;
import lombok.Getter;

@Getter
public class ExternalApiException extends BusinessException {

    public ExternalApiException(String externalApi) {
        super(String.format("External API [%s] Communication Error", externalApi), ErrorCode.EXTERNAL_SERVER_ERROR);
    }

}
