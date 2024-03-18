package com.verifit.verifit.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    OAUTH2_PROVIDER_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "oauth2 provider not supported"),

    ;

    private final HttpStatus httpStatus;
    private final String message;
}
