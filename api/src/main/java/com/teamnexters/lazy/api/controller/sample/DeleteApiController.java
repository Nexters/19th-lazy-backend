package com.teamnexters.lazy.api.controller.sample;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DeleteApiController {

    // http://localhost:9093/api/delete
    @DeleteMapping("/delete/{userId}")
    public String delete(@PathVariable Long userId,
                         @RequestParam String account) {
        System.out.println("userId = " + userId);
        System.out.println("account = " + account);
        return "삭제완료";
    }

}
