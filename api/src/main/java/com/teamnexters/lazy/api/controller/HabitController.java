package com.teamnexters.lazy.api.controller;

import com.teamnexters.lazy.api.domain.HabitDto;
import com.teamnexters.lazy.api.service.HabitService;
import com.teamnexters.lazy.common.domain.habit.Habit;
import com.teamnexters.lazy.common.domain.member.Member;
import com.teamnexters.lazy.common.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Habit Controller", description = "습관과 미루기 관련 API")
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class HabitController {

    private HabitService habitService;

    @Operation(summary = "✅ 습관 추가 API",
            description = "습관을 추가해요.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "[Ok] Create Habit",
                            content = @Content(schema = @Schema(implementation = HabitDto.HabitRes.class))),
                    @ApiResponse(responseCode = "421", description = "[Error] Duplicate Habit Name",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping("/v1/habit")
    public ResponseEntity<HabitDto.HabitRes> saveHabit(
            @Parameter(hidden = true) Authentication authentication,
            @Parameter(description = "습관 정보", required = true) @RequestBody @Valid HabitDto.AddHabitReq dto) {
        Member member = (Member) authentication.getPrincipal();
        return ResponseEntity.ok().body(habitService.create(dto, member.getMemIdx()));
    }

    @Operation(summary = "✅ 전체 미루기 현황 조회 API",
            description = "습관의 전체 미루기 현황을 조회해요.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "[Ok] Get All Habit List",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = HabitDto.AllHabitRes.class))))})
    @GetMapping("/v1/habit")
    public ResponseEntity<Page<Habit>> getHabitList(
            @Parameter(hidden = true) Authentication authentication,
            @Parameter(description = "시작 페이지 (default = 0)") @RequestParam("page") Integer page,
            @Parameter(description = "페이지 사이즈 (default = 3)") @RequestParam("size") Integer size) {
        Member member = (Member) authentication.getPrincipal();
        if (page == null) page = 0;
        if (size == null) size = 3;
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok().body(habitService.getHabitList(member.getMemIdx(), pageRequest));
    }

    @Operation(summary = "✅ 습관 삭제 API",
            description = "습관을 삭제해요.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "[Ok] Delete Habit",
                            content = @Content(schema = @Schema(implementation = HabitDto.HabitIndexRes.class)))})
    @DeleteMapping("/v1/habit/{habit-idx}")
    public ResponseEntity<Long> deleteHabit(
            @Parameter(hidden = true) Authentication authentication,
            @Parameter(description = "습관 번호", required = true) @PathVariable(name = "habit-idx") Long habitIdx) {
        Member member = (Member) authentication.getPrincipal();
        return ResponseEntity.ok().body(habitService.deleteHabit(member.getMemIdx(), habitIdx));
    }


}
