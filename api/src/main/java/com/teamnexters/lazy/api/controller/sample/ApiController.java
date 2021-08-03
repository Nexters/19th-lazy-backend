package com.teamnexters.lazy.api.controller.sample;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "PingPong API", description = "서버 연결 Test API")
@RestController
@RequestMapping("/api")
public class ApiController {

    /**
     * API ping 테스트
     * @return 단순 String
     */
    @Operation(summary = "✅ Connect Test API"
        , description = "연결이 잘 된다면 pong 이 날아가요")
    @GetMapping("/ping")
    public String pingPong() {
        return "pong";
    }

}
