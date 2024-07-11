package com.verifit.verifit.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    // auth
    OAUTH2_PROVIDER_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "oauth2 provider not supported"),

    // member
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "account not found"),
    EMAIL_IS_ALREADY_IN_USE(HttpStatus.CONFLICT, "email already in use"),
    NICKNAME_IS_ALREADY_IN_USE(HttpStatus.CONFLICT, "nickname already in use"),

    // point
    REQUEST_POINT_IS_NEGATIVE(HttpStatus.BAD_REQUEST, "request point is negative"),

    // ranking
    RANKING_NOT_AVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "ranking is currently not available"),
    INVALID_RANKING_PERIOD(HttpStatus.BAD_REQUEST, "invalid ranking period"),

    // general
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "server error"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
