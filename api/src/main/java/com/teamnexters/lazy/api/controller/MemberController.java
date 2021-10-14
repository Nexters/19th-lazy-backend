package com.teamnexters.lazy.api.controller;

import com.teamnexters.lazy.api.domain.MemberDto;
import com.teamnexters.lazy.api.service.MemberService;
import com.teamnexters.lazy.common.domain.member.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Member Controller", description = "회원 정보 관련 컨트롤러")
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private MemberService memberService;

    @Operation(summary = "✅ 단일 회원 정보 조회 API",
            description = "회원의 상세 정보를 조회해요.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "[Ok] Get One Member Info",
                            content = @Content(schema = @Schema(implementation = MemberDto.AllRes.class)))})
    @GetMapping("/v1/member/{mem-idx}")
    public ResponseEntity<Member> getOneMember(
            @Parameter(description = "회원 번호", required = true) @PathVariable(name="mem-idx") Long idx) {
                return ResponseEntity.ok().body(memberService.getOneMember(idx));
    }


    @Operation(summary = "✅ 닉네임 수정 API",
            description = "회원의 닉네임을 수정해요.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "[Ok] Update Member NickName",
                            content = @Content(schema = @Schema(implementation = MemberDto.IndexRes.class)))})
    @PutMapping("/v1/member")
    public ResponseEntity<Long> updateMemberNickName(
            @Parameter(description = "업데이트할 회원 정보", required = true) @RequestBody @Valid MemberDto.UpdateNickNameReq dto) {
        memberService.updateNickName(dto);
        return ResponseEntity.ok().body(dto.getMemIdx());
    }
}
