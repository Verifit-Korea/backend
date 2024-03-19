package com.verifit.verifit.mypage.service;

import com.verifit.verifit.member.entity.Member;
import com.verifit.verifit.member.service.MemberService;
import com.verifit.verifit.mypage.dto.MemberInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MypageService {
    private final MemberService memberService;

    public MemberInfoDTO getMyInfoDTO(Member member) {
        MemberInfoDTO memberInfoDTO = new MemberInfoDTO(member);
        memberInfoDTO.setNickname(member.getNickname());
        memberInfoDTO.setEmail(member.getEmail());
        memberInfoDTO.setProfileUrl(member.getProfileUrl());
        return memberInfoDTO;
    }
}
