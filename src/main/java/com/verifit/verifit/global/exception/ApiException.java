package com.verifit.verifit.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;

    public ApiException(ExceptionCode e){
        this.httpStatus = e.getHttpStatus();
        this.message = e.getMessage();
    }
}