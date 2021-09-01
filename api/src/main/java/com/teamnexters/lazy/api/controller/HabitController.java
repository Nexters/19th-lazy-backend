package com.teamnexters.lazy.api.controller;

import com.teamnexters.lazy.api.domain.HabitDto;
import com.teamnexters.lazy.api.service.HabitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Habit Controller", description = "습관과 미루기 관련 API")
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class HabitController {

    private HabitService habitService;

    @Operation(summary = "✅ 습관 추가 API",
            description = "습관을 추가해요.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = HabitDto.Res.class)))),
    })
    @PostMapping("/v1/habit")
    public ResponseEntity<HabitDto.Res> saveHabit(@RequestBody @Valid HabitDto.AddReq dto) {
        return ResponseEntity.ok().body(habitService.create(dto));
    }


}
