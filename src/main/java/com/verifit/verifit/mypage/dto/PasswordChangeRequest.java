package com.verifit.verifit.mypage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordChangeRequest {
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String currentPassword;
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String newPassword;
}
