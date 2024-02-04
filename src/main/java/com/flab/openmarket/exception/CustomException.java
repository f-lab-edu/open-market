package com.flab.openmarket.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private ErrorCode errorCode;
    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
