package com.verifit.verifit.member.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Member {
    public static final String DEFAULT_PROFILE_URL = "https://";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "닉네임은 필수입니다.")
    @Length(min = 8, max = 20, message = "닉네임은 8자 이상 20자 이하만 가능합니다.")
    private String nickname;

    @Column
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식을 지켜주세요.")
    private String email;

    @Column
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @Column
    @URL(protocol = "https", message = "올바른 주소를 입력해주세요.")
    @NotBlank(message = "프로필 URL은 필수입니다.")
    private String profileUrl;

    public void changePassword(String newPassword) {
        password = newPassword;
    }

    public void changeNickname(String newNickname) {
        nickname = newNickname;
    }

    public void updateProfileUrl(String newProfileUrl) {
        profileUrl = newProfileUrl;
    }
}
