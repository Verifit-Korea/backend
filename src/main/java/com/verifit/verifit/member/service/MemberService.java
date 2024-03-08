package com.verifit.verifit.member.service;

import com.verifit.verifit.member.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public boolean isEmailAlreadyExists(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean isNicknameAlreadyExists(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }
}
