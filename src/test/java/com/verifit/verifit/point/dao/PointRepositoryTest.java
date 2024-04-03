package com.verifit.verifit.point.dao;

import com.verifit.verifit.member.dao.MemberRepository;
import com.verifit.verifit.member.entity.Member;
import com.verifit.verifit.point.domain.entity.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PointRepositoryTest {

    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private MemberRepository memberRepository;
    Member member;

    @BeforeEach
    void setUp(){
        Member testMember = Member.createMember("김김김김김김김김김", "a@a.a", "password", "https://aaa.aaa");
        this.member = memberRepository.save(testMember);
    }

    @DisplayName("회원의 월별 누적 포인트를 조회한다.")
    @Test
    void findMemberMonthlyAccumulatedPoint() {
        // given
        for (int i = 1; i <= 3; i++) {

            Point point = Point.builder()
                    .member(member)
                    .accumulatedPoint(i * 2000)
                    .availablePoint(i * 1000)
                    .startDateTime(LocalDateTime.of(2024, 4, i * 7, 0, 0, 0))
                    .endDateTime(LocalDateTime.of(2024, 4, i * 7 + 6, 0, 0, 0))
                    .build();

            pointRepository.save(point);
        }

        LocalDateTime dateTime = LocalDateTime.of(2024, 4, 1, 0, 0, 0);

        // when
        long accumulatedPoint = pointRepository.findMemberMonthlyAccumulatedPoint(member.getId(), dateTime);

        // then
        assertThat(accumulatedPoint).isEqualTo(12000);
    }
    
    @DisplayName("회원의 시즌 포인트를 조회한다.")
    @Test
    void findMemberPointBySeason() {
        // given
        Point point = Point.builder()
                .member(member)
                .accumulatedPoint(2000)
                .availablePoint(1000)
                .startDateTime(LocalDateTime.of(2024, 4, 7, 0, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 4, 13, 23, 59, 59))
                .build();

        pointRepository.save(point);

        LocalDateTime dateTime = LocalDateTime.of(2024, 4, 9, 0, 0, 0);
        
        // when
        Point findPoint = pointRepository.findMemberPointByDateTime(member.getId(), dateTime).get();

        // then
        assertThat(findPoint.getMember().getId()).isEqualTo(member.getId());
        assertThat(findPoint.getAccumulatedPoint()).isEqualTo(2000);
        assertThat(findPoint.getAvailablePoint()).isEqualTo(1000);
        assertThat(dateTime).isBetween(findPoint.getStartDateTime(), findPoint.getEndDateTime());
    }
    
    @DisplayName("회원의 사용 가능한 포인트를 조회한다.")
    @Test
    void findMemberAvailablePoint() {
        // given
        for (int i = 1; i <= 3; i++) {

            Point point = Point.builder()
                    .member(member)
                    .accumulatedPoint(i * 2000)
                    .availablePoint(i * 1000)
                    .startDateTime(LocalDateTime.of(2024, 4, i * 7, 0, 0, 0))
                    .endDateTime(LocalDateTime.of(2024, 4, i * 7 + 6, 0, 0, 0))
                    .build();

            pointRepository.save(point);
        }

        LocalDateTime dateTime = LocalDateTime.of(2024, 4, 26, 0, 0, 0);
        
        // when
        long availablePoint = pointRepository.findMemberAvailablePoint(member.getId(), dateTime);

        // then
        assertThat(availablePoint).isEqualTo(5000);
    }
}