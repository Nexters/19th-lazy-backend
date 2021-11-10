package com.teamnexters.lazy.api.domain.oauth.apple;

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