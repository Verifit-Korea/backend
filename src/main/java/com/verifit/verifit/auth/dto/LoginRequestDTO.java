package com.verifit.verifit.auth.dto;

import lombok.Getter;

@Getter
public class LoginRequestDTO {
    private String email;
    private String password;
}
