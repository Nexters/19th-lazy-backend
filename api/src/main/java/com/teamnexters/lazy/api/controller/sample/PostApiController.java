package com.teamnexters.lazy.api.controller.sample;

import com.teamnexters.lazy.api.domain.sampleDto.PostRequestDto;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PostApiController {

    @PostMapping("/post")
    public void post(@RequestBody Map<String, Object> requestData) {
        requestData.forEach((key, value) -> {
            System.out.println("key = " + key);
            System.out.println("value = " + value);
        });
    }

    @PostMapping("/post02")
    public void postVer2(@RequestBody PostRequestDto requestDto) {
        System.out.println("requestDto = " + requestDto.toString());
    }

}
