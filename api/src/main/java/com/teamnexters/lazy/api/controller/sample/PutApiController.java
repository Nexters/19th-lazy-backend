package com.teamnexters.lazy.api.controller.sample;

import com.teamnexters.lazy.api.domain.sampleDto.PutRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PutApiController {

    // http://localhost:9093/api/put
    @PutMapping("/put")
    public PutRequestDto putVer1(@RequestBody PutRequestDto requestDto) {
        System.out.println("requestDto = " + requestDto.toString());
        return requestDto;
    }

    // http://localhost:9093/api/put
    @PutMapping("/put/{userId}")
    public PutRequestDto putVer2(@RequestBody PutRequestDto requestDto, @PathVariable Long userId) {
        System.out.println("requestDto = " + requestDto.toString());
        System.out.println("userId = " + userId);
        return requestDto;
    }

    // http://localhost:9093/api/put/response-entity
    @PutMapping("/put/response-entity")
    public ResponseEntity<PutRequestDto> putVer3(@RequestBody PutRequestDto requestDto) {
        System.out.println("requestDto = " + requestDto.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(requestDto);
    }


}
