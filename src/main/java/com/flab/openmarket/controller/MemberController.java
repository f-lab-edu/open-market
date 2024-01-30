package com.flab.openmarket.controller;

import com.flab.openmarket.dto.MemberCreateRequestDto;
import com.flab.openmarket.common.ApiResponse;
import com.flab.openmarket.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/users")
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createUser(@Valid @RequestBody MemberCreateRequestDto createRequestDto) {
        memberService.createUser(createRequestDto);

        return ApiResponse.created();
    }
}
