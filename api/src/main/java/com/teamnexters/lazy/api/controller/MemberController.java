package com.teamnexters.lazy.api.controller;

import com.teamnexters.lazy.api.service.MemberService;
import com.teamnexters.lazy.common.domain.member.Member;
import com.teamnexters.lazy.common.domain.member.MemberDto;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MemberController {

    private MemberService memberService;

    // POST http://localhost:9093/v1/member
    @PostMapping("/v1/member")
    public ResponseEntity<MemberDto.Res> getMemberList(@RequestBody @Valid MemberDto.SignUpReq dto) {
        return ResponseEntity.ok().body(memberService.create(dto));
    }

    // GET http://localhost:9093/v1/member
//    @GetMapping("/v1/member")
//    public ResponseEntity<MemberDto.Res> getMemberList() {
//        return name;
//    }
}
