package com.flab.openmarket.dto;

import com.flab.openmarket.model.MemberRole;
import com.flab.openmarket.validation.Enum;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCreateRequest {
    @NotBlank
    @Size(min = 5, max = 80)
    @Email
    private final String email;

    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9!@#$%^&*()_+])[a-zA-Z0-9!@#$%^&*()_+]+$")
    private final String passwd;

    @NotBlank
    @Size(min = 2, max = 40)
    @Pattern(regexp = "^[a-zA-Z가-힣]*$")
    private final String name;

    @NotBlank
    @Size(min = 10, max = 20)
    @Pattern(regexp = "^[0-9]*$")
    private final String phone;

    @NotBlank
    @Enum(enumClass = MemberRole.class, ignoreCase = true)
    private final String role;

    @Builder
    private MemberCreateRequest(String email, String passwd, String name, String phone, String role) {
        this.email = email;
        this.passwd = passwd;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }
}
