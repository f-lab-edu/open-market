package com.flab.openmarket.controller;

import com.flab.openmarket.dto.MemberCreateRequest;
import com.flab.openmarket.common.ApiResponse;
import com.flab.openmarket.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/users")
@RestController
public class MemberController {
    private final MemberService memberService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public ApiResponse<Long> signup(@Valid @RequestBody MemberCreateRequest createRequestDto) {
        Long memberId = memberService.signup(createRequestDto);

        return ApiResponse.created(memberId);
    }
}
