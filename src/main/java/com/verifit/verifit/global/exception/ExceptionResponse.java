package com.verifit.verifit.global.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
public record ExceptionResponse(

        int resultCode,
//        HttpStatus httpStatus,
        String message,
        LocalDateTime timestamp,
        String path
) {
    public static ExceptionResponse from(ApiException e, String path) {
        return ExceptionResponse.builder()
                .resultCode(e.getHttpStatus().value())
//                .httpStatus(e.getHttpStatus())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }

    public static ExceptionResponse of(HttpStatus httpStatus, String message, String path) {
        return ExceptionResponse.builder()
                .resultCode(httpStatus.value())
//                .httpStatus(httpStatus)
                .message(message)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }
}
