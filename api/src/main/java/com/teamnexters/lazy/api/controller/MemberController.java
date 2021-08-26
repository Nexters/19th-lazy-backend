package com.teamnexters.lazy.api.controller;

import com.teamnexters.lazy.api.service.MemberService;
import com.teamnexters.lazy.common.domain.member.Member;
import com.teamnexters.lazy.common.domain.member.MemberDto;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member Controller", description = "회원 정보 관련 컨트롤러")
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private MemberService memberService;

    @Operation(summary = "✅ 단일 회원 정보 조회 API",
            description = "회원의 상세 정보를 조회.", tags = { "contact" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contact.class)))) })
    @GetMapping("/v1/member/{mem-idx}")
    public ResponseEntity<Member> getOneMember(@PathVariable(name="mem-idx") Long idx) {
                return ResponseEntity.ok().body(memberService.getOneMember(idx));
    }

    @Operation(summary = "✅ 회원 전체 조회 API",
            description = " 전체 회원 목록 List 조회", tags = { "contact" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공 코드",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contact.class)))) })
    // GET http://localhost:9093/v1/member
    @GetMapping("/v1/member")
    public ResponseEntity<List<Member>> getMemberList() {
        return ResponseEntity.ok().body(memberService.getMemberList());
    }
}
