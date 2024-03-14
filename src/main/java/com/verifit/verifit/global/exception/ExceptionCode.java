package com.verifit.verifit.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    ;

    private final HttpStatus httpStatus;
    private final String message;
}
