package com.verifit.verifit.auth.dto;

import lombok.Getter;

@Getter
public class RegisterRequestDTO {
    private String email;
    private String password;
    private String nickname;
}
