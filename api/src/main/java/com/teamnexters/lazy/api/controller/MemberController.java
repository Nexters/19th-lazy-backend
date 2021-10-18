package com.teamnexters.lazy.api.controller;

import com.teamnexters.lazy.api.domain.MemberDto;
import com.teamnexters.lazy.api.exception.MemberNotFoundException;
import com.teamnexters.lazy.api.service.MemberService;
import com.teamnexters.lazy.common.domain.member.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Member Controller", description = "회원 정보 관련 컨트롤러")
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api")
public class MemberController {

    private MemberService memberService;

    @Operation(summary = "✅ 단일 회원 정보 조회 API",
            description = "회원의 상세 정보를 조회해요.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "[Ok] Get One Member Info",
                            content = @Content(schema = @Schema(implementation = MemberDto.AllMemberRes.class)))})
    @GetMapping("/v1/member")
    public ResponseEntity<Member> getOneMember(
            @Parameter(hidden = true) Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        log.info(">>> authentication Principal : {}", member);

        if (member == null) {
            throw new MemberNotFoundException("Auth");
        } else {
            log.info(">>> Member info : {}", member);
            return ResponseEntity.ok().body(member);
        }
    }


    @Operation(summary = "✅ 닉네임 수정 API",
            description = "회원의 닉네임을 수정해요.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "[Ok] Update Member NickName",
                            content = @Content(schema = @Schema(implementation = MemberDto.MemberIndexRes.class)))})
    @PutMapping("/v1/member")
    public ResponseEntity<Long> updateMemberNickName(
            @Parameter(hidden = true) Authentication authentication,
            @Parameter(description = "업데이트할 회원 정보", required = true) @RequestBody @Valid MemberDto.UpdateNickNameReq dto) {
        Member member = (Member) authentication.getPrincipal();
        Long memIdx = member.getMemIdx();
        log.debug(">>> Update Member No [{}] NickName : {}", memIdx, dto);

        memberService.updateNickName(dto, memIdx);
        return ResponseEntity.ok().body(memIdx);
    }
}
