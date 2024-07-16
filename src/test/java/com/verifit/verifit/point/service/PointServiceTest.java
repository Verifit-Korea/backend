package com.verifit.verifit.point.service;

import com.verifit.verifit.global.exception.ApiException;
import com.verifit.verifit.global.exception.ExceptionCode;
import com.verifit.verifit.member.dao.MemberRepository;
import com.verifit.verifit.member.entity.Member;
import com.verifit.verifit.point.domain.entity.PointHistory;
import com.verifit.verifit.point.dto.PointDTO;
import com.verifit.verifit.point.repository.PointHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PointServiceTest {

    @Mock
    private PointHistoryRepository pointHistoryRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private PointService pointService;

    private Member member;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        member = Member.builder()
                .id(1L)
                .nickname("test")
                .availablePoint(100)
                .accumulatedPoint(200)
                .build();
    }

    @Test
    @DisplayName("단일 멤버 포인트 조회")
    public void testGetMemberPoint(){
        // given

        // when
        PointDTO result = pointService.getMemberPoint(member);

        // then
        assertNotNull(result);
        assertEquals(100, result.getAvailablePoints());
        assertEquals(200, result.getAccumulatedPoints());
    }
    @Test
    @DisplayName("멤버가 없는 경우 API 예외 발생")
    public void testGetMemberPointMemberNotFound() {
        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            pointService.getMemberPoint(null);
        });

        assertEquals("account not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }


    @Test
    @Transactional
    @DisplayName("포인트 적립")
    void addPoint() {
        when(pointHistoryRepository.save(any(PointHistory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        pointService.addPoint(member, 50);

        assertEquals(150, member.getAvailablePoint());
        assertEquals(250, member.getAccumulatedPoint());
    }

    @Test
    @Transactional
    @DisplayName("포인트 사용")
    void usePoint() {
        pointService.usePoint(member, 50);

        assertEquals(50, member.getAvailablePoint());
        assertEquals(200, member.getAccumulatedPoint());
    }

    @Test
    @Transactional
    @DisplayName("포인트 사용 - 포인트 부족")
    public void testUsePointNotEnoughPoint(){
        ApiException exception = assertThrows(ApiException.class, () -> {
            pointService.usePoint(member, 150);
        });

        assertEquals("request point is negative", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

}