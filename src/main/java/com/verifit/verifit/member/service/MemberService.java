package com.verifit.verifit.member.service;

import com.verifit.verifit.global.exception.ApiException;
import com.verifit.verifit.global.exception.ExceptionCode;
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
    private final PasswordEncoder passwordEncoder; // SecurityConfig에서 Bean으로 등록한 PasswordEncoder

    public boolean isEmailAlreadyExists(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean isNicknameAlreadyExists(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ExceptionCode.ACCOUNT_NOT_FOUND));
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ExceptionCode.ACCOUNT_NOT_FOUND));
    }


    @Transactional
    public void updateEmail(Long memberId, String newEmail) {
        // 사용자 정보 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ExceptionCode.ACCOUNT_NOT_FOUND));
        // 이메일 변경
        member.changeEmail(newEmail);
    }

    @Transactional
    public void changePassword(Long memberId, String oldPassword, String newPassword) {
        // 사용자 정보 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ExceptionCode.ACCOUNT_NOT_FOUND));
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
    public  void changePasswordWithoutOldPassword(Long memberId, String newPassword) {
        // 사용자 정보 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ExceptionCode.ACCOUNT_NOT_FOUND));
        // 새 비밀번호 암호화
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        // 비밀번호 변경
        member.changePassword(encodedNewPassword);
    }

    @Transactional
    public void updateNickname(Long memberId, String nickname) {
        // 사용자 정보 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ExceptionCode.ACCOUNT_NOT_FOUND));
        // 닉네임 변경
        member.changeNickname(nickname);
    }

    @Transactional
    public void updateProfileUrl(Long memberId, String newProfileUrl) {
        // 사용자 정보 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ExceptionCode.ACCOUNT_NOT_FOUND));
        // 프로필 URL 변경
        member.updateProfileUrl(newProfileUrl);
    }

}
