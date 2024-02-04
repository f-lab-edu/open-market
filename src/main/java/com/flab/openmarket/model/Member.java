package com.flab.openmarket.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Member {
    private Long memberId;
    private String email;
    private String name;
    private String password;
    private String phone;
    private MemberRole role; // todo: enum
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    private Member(String email, String name, String password, String phone, MemberRole role, LocalDateTime createdAt,
                   LocalDateTime updatedAt
    ) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
