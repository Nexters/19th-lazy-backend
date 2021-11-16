package com.teamnexters.lazy.api.external.Apple;

import com.teamnexters.lazy.api.domain.oauth.apple.ApplePublicKeyResponse;
import com.teamnexters.lazy.api.domain.oauth.apple.AppleToken;
import com.teamnexters.lazy.api.exception.ExternalApiException;
import com.teamnexters.lazy.api.exception.TokenValidException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AppleClient {

    private final WebClient webClient;

    // Apple 외부 통신 URL
    private static final String APPLE_API_URL = "https://appleid.apple.com/auth";

    public ApplePublicKeyResponse getAppleAuthPublicKey() {

        return webClient.get()
                .uri(APPLE_API_URL)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenValidException()))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new ExternalApiException("GET : " + APPLE_API_URL)))
                .bodyToMono(ApplePublicKeyResponse.class)
                .block();
    }

    public AppleToken.Response getAppleToken(AppleToken.Request request) {

        return webClient.post()         // POST Method
                .uri(APPLE_API_URL)     // APPLE URL
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(request)     // JSON Body
                .retrieve()             // Client 메시지 전송
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenValidException()))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new ExternalApiException("GET : " + APPLE_API_URL)))
                .bodyToMono(AppleToken.Response.class)
                .block();               // await
    }
}
