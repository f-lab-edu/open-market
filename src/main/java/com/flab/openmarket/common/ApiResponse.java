package com.flab.openmarket.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private String message;

    public ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public ApiResponse(String message) {
        this.message = message;
    }

    public static <T>ResponseEntity<ApiResponse<T>> ok(T data, String message) {
        ApiResponse<T> apiResponse = new ApiResponse<>(data, message);
        return ResponseEntity.ok(apiResponse);
    }

    public static <T>ResponseEntity<ApiResponse<T>> created(T data, String message) {
        ApiResponse<T> apiResponse = new ApiResponse<>(data, message);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    public static <T>ResponseEntity<ApiResponse<T>> created(T data) {
        ApiResponse<T> apiResponse = new ApiResponse<>(data, "created");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    public static <T>ResponseEntity<ApiResponse<T>> created() {
        ApiResponse<T> apiResponse = new ApiResponse<>("created");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

}
