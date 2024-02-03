package com.flab.openmarket.exception;

import com.flab.openmarket.common.ApiResponse;
import com.flab.openmarket.common.ErrorReason;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sb = new StringBuilder();

        bindingResult.getAllErrors()
                .forEach(error -> {
                    FieldError field = (FieldError) error;
                    String message = error.getDefaultMessage();

                    sb.append("field: ").append(field.getField());
                    sb.append(", message: ").append(message);
                });

        return ApiResponse.error(HttpStatus.BAD_REQUEST, sb.toString());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Object>> handleCustomException(CustomException e) {
        ErrorReason errorReason = e.getErrorCode().getErrorReason();

        return ResponseEntity
                .status(errorReason.getStatus())
                .body(ApiResponse.error(errorReason));
    }
}
