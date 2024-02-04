package com.flab.openmarket.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.flab.openmarket.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;

@Getter
public class ApiResponse<T> {
    private Integer status;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ApiResponse(HttpStatus status, String message, T data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }

    public ApiResponse(HttpStatus status, String code, String message) {
        this.status = status.value();
        this.code = code;
        this.message = message;
    }

    public static <T>ApiResponse<T> of(HttpStatus httpStatus, String message, T data) {
        return new ApiResponse<>(httpStatus, message, data);
    }

    public static <T>ApiResponse<T> of(HttpStatus httpStatus, T data) {
        return of(httpStatus, httpStatus.name(), data);
    }

    public static <T>ApiResponse<T> of(HttpStatus httpStatus, String message) {
        return of(httpStatus, message, null);
    }

    public static <T>ApiResponse<T> ok(T data) {
        return of(HttpStatus.OK, HttpStatus.OK.name(), data);
    }

    public static <T>ApiResponse<T> created(T data) {
        return of(HttpStatus.CREATED, HttpStatus.CREATED.name(), data);
    }

    public static <T>ApiResponse<T> error(HttpStatus status, String message) {
        return of(status, message);
    }

    public static <T>ApiResponse<T> error(ErrorReason errorReason) {
        return new ApiResponse<>(errorReason.getStatus(), errorReason.getCode(), errorReason.getReason());
    }
}
