package com.teamnexters.lazy.api.controller.sample;

import com.teamnexters.lazy.api.domain.sampleDto.UserRequest;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/get")
public class GetApiController {

    @GetMapping(path = "/hello")
    public String hello() {
        return "get Hello";
    }

    // http://localhost:9093/api/get/path-variable/name
    @GetMapping("/path-variable/{name}")
    public String pathVariable(@PathVariable String name) {
        System.out.println("PathVariable = " + name);
        return name;
    }

    // http://localhost:9093/api/get/path-variable/name
    @GetMapping("/path-variable/{mem-idx}")
    public String pathVariableName(@PathVariable(name="mem-idx") String idx) {
        System.out.println("PathVariable = " + idx);
        return idx;
    }

    // http://localhost:9093/api/get/query-param?user=steve&email=steve@gmail.com&age=30
    @GetMapping("/query-param")
    public String queryParam(@RequestParam Map<String, String> queryParam) {

        StringBuilder sb = new StringBuilder();

        queryParam.forEach((key, value) -> {
            System.out.println("entry getKey = " + key);
            System.out.println("entry getValue = " + value);
            System.out.println();

            sb.append(key).append(" = ").append(value);
        });

        return sb.toString();
    }

    // http://localhost:9093/api/get/query-param02?name=steve&email=steve@gmail.com&age=30
    @GetMapping("query-param02")
    public String queryParam02( @RequestParam String name,
                                @RequestParam String email,
                                @RequestParam int age) {
        System.out.println("name = " + name);
        System.out.println("email = " + email);
        System.out.println("age = " + age);

        return name + " " + email + " " + age;
    }


    // http://localhost:9093/api/get/query-param02?name=steve&email=steve@gmail.com&age=30
    @GetMapping("query-param03")
    public String queryParam03(UserRequest userRequest) {
        System.out.println("name = " + userRequest.getName());
        System.out.println("email = " + userRequest.getEmail());
        System.out.println("age = " + userRequest.getAge());

        return userRequest.toString();
    }

}
