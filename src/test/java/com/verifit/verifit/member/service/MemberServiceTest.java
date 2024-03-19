package com.verifit.verifit.member.service;

import com.verifit.verifit.member.dao.MemberRepository;
import com.verifit.verifit.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private MemberService memberService;

    private Member member;

    @BeforeEach
    void setUp(){
        member = Member.createMember("oldNickname", "oldEmail@gmail.com", "oldPassword", "oldProfileUrl");
        member.setId(1L);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedNewPassword");
    }

    @Test
    void changePassword() {
        // given
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        // when
        memberService.changePassword(1L, oldPassword, newPassword);
        // then
        System.out.println("member.getPassword() = " + member.getPassword());
        assertTrue(passwordEncoder.matches("newPassword", member.getPassword()));
    }

    @Test
    void updateNickname() {
        // given
        String newNickname = "newNickname";
        // when
        memberService.updateNickname(1L, newNickname);
        // then
        assertEquals(member.getNickname(), newNickname);
    }

    @Test
    void updateProfileUrl() {
        // given
        String newProfileUrl = "newProfileUrl";
        // when
        memberService.updateProfileUrl(1L, newProfileUrl);
        // then
        assertEquals(member.getProfileUrl(), newProfileUrl);
    }
}