package com.verifit.verifit.member.service;

import com.verifit.verifit.member.dao.MemberRepository;
import com.verifit.verifit.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean isEmailAlreadyExists(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean isNicknameAlreadyExists(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    @Transactional
    public void changePassword(Long memberId, String oldPassword, String newPassword) {
        // 사용자 정보 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 존재하지 않습니다."));
        // 기존 비밀번호 확인
        if (!passwordEncoder.matches(oldPassword, member.getPassword())) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }
        // 새 비밀번호 암호화
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        // 비밀번호 변경
        member.changePassword(encodedNewPassword);
    }

    @Transactional
    public void updateNickname(Long memberId, String nickname) {
        // 사용자 정보 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 존재하지 않습니다."));
        // 닉네임 변경
        member.changeNickname(nickname);
    }

    @Transactional
    public void updateProfileUrl(Long memberId, String newProfileUrl) {
        // 사용자 정보 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 존재하지 않습니다."));
        // 프로필 URL 변경
        member.updateProfileUrl(newProfileUrl);
    }

}
