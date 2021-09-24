package com.teamnexters.lazy.api.exception;

import com.teamnexters.lazy.common.error.ErrorCode;
import com.teamnexters.lazy.common.error.exception.BusinessException;
import lombok.Getter;

@Getter
public class OAuth2ProviderNotMatchException extends BusinessException {

    public OAuth2ProviderNotMatchException(String provider) {
        super(String.format("Provider [%s] is not support", provider), ErrorCode.OAUTH_PROVIDER_NOT_SUPPORT);
    }

}
