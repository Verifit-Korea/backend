package com.verifit.verifit.mypage.service;

import com.verifit.verifit.member.entity.Member;
import com.verifit.verifit.member.service.MemberService;
import com.verifit.verifit.mypage.dto.MemberInfoDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MypageServiceTest {

    private MemberService memberServiceMock;
    private MypageService mypageService;


    @BeforeEach
    public void setUp() {
        memberServiceMock = mock(MemberService.class);
        mypageService = new MypageService(memberServiceMock);
    }

    @Test
    public void testGetMyInfo() {
        // given
        Member testMember = Member.createMember("testNickname",
                "testEmail@gmail.com",
                "testpassword",
                "testProfileUrl");
        // when
        MemberInfoDTO resultDTO = mypageService.getMyInfoDTO(testMember);

        // then
        System.out.println("resultDTO = " + resultDTO);
        assertEquals(resultDTO.getNickname(), "testNickname");
        assertEquals(resultDTO.getEmail(), "testEmail@gmail.com");
        assertEquals(resultDTO.getProfileUrl(), "testProfileUrl");
    }
}