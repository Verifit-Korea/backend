package com.verifit.verifit.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    OAUTH2_PROVIDER_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "oauth2 provider not supported"),


    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "account not found"),
    EMAIL_IS_ALREADY_IN_USE(HttpStatus.CONFLICT, "email already in use"),
    NICKNAME_IS_ALREADY_IN_USE(HttpStatus.CONFLICT, "nickname already in use"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
