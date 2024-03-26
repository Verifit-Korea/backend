package com.verifit.verifit.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.verifit.verifit.global.jwt.JwtProvider;
import com.verifit.verifit.member.dao.MemberRepository;
import com.verifit.verifit.member.entity.Member;
import com.verifit.verifit.member.service.MemberService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class AuthServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private MemberService memberService;
    @InjectMocks
    private AuthService authService;

    private Member member;
    @BeforeEach
    void setUp(){
        member = Member.createMember("nickname", "email@gmail.com", "password", "profileUrl");
        member.setId(1L);
        when(memberRepository.findByEmail("email@gmail.com")).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedNewPassword");
        when(jwtProvider.generateAccessToken(any())).thenReturn("validToken");
        when(memberService.isEmailAlreadyExists(any())).thenReturn(false);
        when(memberService.isNicknameAlreadyExists(any())).thenReturn(false);
    }

    @Test
    void loginUsingPassword() {
        // given
        String email = "email@gmail.com";
        String password = "password";
        // when
        String token = authService.loginUsingPassword(email, password);
        // then
        assertEquals(token, "validToken");
    }

    @Test
    void registerUsingPassword() {
        // given
        String email = "newEmail@gmail.com";
        String password = "newPassword";
        String nickname = "newNickname";
        // when
        authService.registerUsingPassword(email, password, nickname);
        // then
    }
}