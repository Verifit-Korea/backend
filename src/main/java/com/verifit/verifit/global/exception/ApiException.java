package com.verifit.verifit.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;

    public ApiException(ExceptionCode e){
        super(e.getMessage());
        this.httpStatus = e.getHttpStatus();
        this.message = e.getMessage();
    }

    public ApiException(ExceptionCode e, Throwable cause){
        super(e.getMessage(), cause);
        this.httpStatus = e.getHttpStatus();
        this.message = e.getMessage();
    }
}