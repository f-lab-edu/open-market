package com.flab.openmarket.dto;

import com.flab.openmarket.model.MemberRole;
import com.flab.openmarket.validation.Enum;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCreateRequestDto {
    @Size(min = 5, max = 80)
    @Email
    private final String email;

    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9!@#$%^&*()_+])[a-zA-Z0-9!@#$%^&*()_+]{3,}$")
    private final String passwd;

    @Size(min = 2, max = 40)
    @Pattern(regexp = "^[a-zA-Z가-힣]*$")
    private final String name;

    @Size(min = 10, max = 20)
    @Pattern(regexp = "^[0-9]*$")
    private final String phone;

    @NotEmpty
    @Enum(enumClass = MemberRole.class, ignoreCase = true)
    private final String role;

    @Builder
    private MemberCreateRequestDto(String email, String passwd, String name, String phone, String role) {
        this.email = email;
        this.passwd = passwd;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }
}
