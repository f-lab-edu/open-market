package com.flab.openmarket.exception;

import com.flab.openmarket.common.ErrorReason;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode implements ErrorCode{
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "DUPLICATE_EMAIL", "이미 가입된 이메일 입니다."),
    DUPLICATE_PHONE(HttpStatus.BAD_REQUEST, "DUPLICATE_PHONE", "이미 가입된 전화번호 입니다.");

    private final HttpStatus status;
    private final String code;
    private final String reason;


    MemberErrorCode(HttpStatus status, String code, String reason) {
        this.status = status;
        this.code = code;
        this.reason = reason;
    }

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder()
                .status(this.status)
                .code(this.code)
                .reason(this.reason)
                .build();
    }
}
