package com.teamnexters.lazy.api.config;

import com.teamnexters.lazy.api.config.auth.dto.ApplePublicKeyResponse;
import com.teamnexters.lazy.api.config.auth.jwt.AppleToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="appleClient", url="https://appleid.apple.com/auth")
public interface AppleClient {
    @GetMapping(value = "/keys")
    ApplePublicKeyResponse getAppleAuthPublicKey();

    @PostMapping(value = "/token", consumes = "application/x-www-form-urlencoded")
    AppleToken.Response getToken(AppleToken.Request request);

}