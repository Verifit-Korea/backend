package com.verifit.verifit.mypage.dto;

import com.verifit.verifit.member.entity.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

/* 마이페이지에서 내 정보 페이지에서 필요한 정보를 조회하기 위한 DTO */
@Data
@NoArgsConstructor
public class MemberInfoDTO {
    private String nickname;
    private String email;
    private String profileUrl;

    public MemberInfoDTO(Member member) {
        nickname = member.getNickname();
        email = member.getEmail();
        profileUrl = member.getProfileUrl();
    }

}
