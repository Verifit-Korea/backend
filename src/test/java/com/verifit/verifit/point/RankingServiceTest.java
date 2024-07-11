package com.verifit.verifit.point;

import com.amazonaws.services.s3.AmazonS3;
import com.verifit.verifit.global.config.S3Config;
import com.verifit.verifit.global.storage.StorageServiceImpl;
import com.verifit.verifit.member.entity.Member;
import com.verifit.verifit.point.dto.CombinedRankingDTO;
import com.verifit.verifit.point.dto.MemberRankingDTO;
import com.verifit.verifit.point.repository.PointHistoryRepository;
import com.verifit.verifit.point.service.RankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ActiveProfiles("test")
public class RankingServiceTest {

    @Autowired
    private RankingService rankingService;

    @MockBean
    private PointHistoryRepository pointHistoryRepository;

    private Member member1;
    private Member member2;
    private Member member3;
    private Member member4;
    private Member member5;

    @BeforeEach
    public void setUp() {
        member1 = Member.builder()
                .nickname("user1")
                .email("user1@example.com")
                .password("password1")
                .profileUrl(Member.DEFAULT_PROFILE_URL)
                .availablePoint(100)
                .accumulatedPoint(100)
                .build();
        member1.setId(1L);

        member2 = Member.builder()
                .nickname("user2")
                .email("user2@example.com")
                .password("password2")
                .profileUrl(Member.DEFAULT_PROFILE_URL)
                .availablePoint(50)
                .accumulatedPoint(50)
                .build();
        member2.setId(2L);

        member3 = Member.builder()
                .nickname("user3")
                .email("user3@example.com")
                .password("password3")
                .profileUrl(Member.DEFAULT_PROFILE_URL)
                .availablePoint(300)
                .accumulatedPoint(300)
                .build();
        member3.setId(3L);

        member4 = Member.builder()
                .nickname("user4")
                .email("user4@example.com")
                .password("password4")
                .profileUrl(Member.DEFAULT_PROFILE_URL)
                .availablePoint(200)
                .accumulatedPoint(200)
                .build();
        member4.setId(4L);

        member5 = Member.builder()
                .nickname("user5")
                .email("user5@example.com")
                .password("password5")
                .profileUrl(Member.DEFAULT_PROFILE_URL)
                .availablePoint(0)
                .accumulatedPoint(0)
                .build();
        member5.setId(5L);
    }

    @Test
    @DisplayName("일간 랭킹 조회")
    public void testGetDailyRanking() {
        // Given
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        List<MemberRankingDTO> mockRanking = Arrays.asList(
                new MemberRankingDTO(3L, "user3", 300L, 0),
                new MemberRankingDTO(4L, "user4", 200L, 0),
                new MemberRankingDTO(1L, "user1", 100L, 0),
                new MemberRankingDTO(2L, "user2", 50L, 0),
                new MemberRankingDTO(5L, "user5", 0L, 0)
        );

        Mockito.when(pointHistoryRepository.findRankingByPeriod(startOfDay, endOfDay)).thenReturn(mockRanking);

        // When
        List<MemberRankingDTO> ranking = rankingService.getDailyRanking();
        System.out.println("This is Daily Ranking: " + ranking);

        // Then
        assertEquals(5, ranking.size());
        assertEquals(1, ranking.get(0).getRank());
        assertEquals(2, ranking.get(1).getRank());
        assertEquals(3, ranking.get(2).getRank());
        assertEquals(4, ranking.get(3).getRank());
        assertEquals(5, ranking.get(4).getRank());
    }

    @Test
    @DisplayName("주간 랭킹 조회")
    public void testGetWeeklyRanking() {
        // Given
        LocalDateTime startOfWeek = LocalDate.now().with(java.time.DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime endOfWeek = startOfWeek.plusWeeks(1).minusSeconds(1);

        List<MemberRankingDTO> mockRanking = Arrays.asList(
                new MemberRankingDTO(1L, "user1", 100L, 0),
                new MemberRankingDTO(2L, "user2", 50L, 0),
                new MemberRankingDTO(3L, "user3", 300L, 0),
                new MemberRankingDTO(4L, "user4", 200L, 0),
                new MemberRankingDTO(5L, "user5", 0L, 0)
        );

        Mockito.when(pointHistoryRepository.findRankingByPeriod(startOfWeek, endOfWeek)).thenReturn(mockRanking);

        // When
        List<MemberRankingDTO> ranking = rankingService.getWeeklyRanking();
        System.out.println("This is Weekly Ranking: " + ranking);

        // Then
        assertEquals(5, ranking.size());
        assertEquals(1, ranking.get(0).getRank());
        assertEquals(2, ranking.get(1).getRank());
        assertEquals(3, ranking.get(2).getRank());
        assertEquals(4, ranking.get(3).getRank());
        assertEquals(5, ranking.get(4).getRank());
    }

    @Test
    @DisplayName("월간 랭킹 조회")
    public void testGetMonthlyRanking() {
        // Given
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);

        List<MemberRankingDTO> mockRanking = Arrays.asList(
                new MemberRankingDTO(3L, "user3", 300L, 0),
                new MemberRankingDTO(1L, "user1", 100L, 0),
                new MemberRankingDTO(4L, "user4", 200L, 0),
                new MemberRankingDTO(2L, "user2", 50L, 0),
                new MemberRankingDTO(5L, "user5", 0L, 0)
        );

        Mockito.when(pointHistoryRepository.findRankingByPeriod(startOfMonth, endOfMonth)).thenReturn(mockRanking);

        // When
        List<MemberRankingDTO> ranking = rankingService.getMonthlyRanking();
        System.out.println("This is Monthly Ranking: " + ranking);

        // Then
        assertEquals(5, ranking.size());
        assertEquals(1, ranking.get(0).getRank());
        assertEquals(2, ranking.get(1).getRank());
        assertEquals(3, ranking.get(2).getRank());
        assertEquals(4, ranking.get(3).getRank());
        assertEquals(5, ranking.get(4).getRank());
    }

    @Test
    @DisplayName("전체 랭킹 조회")
    public void testGetCombinedRanking() {
        // Given
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        LocalDateTime startOfWeek = LocalDate.now().with(java.time.DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime endOfWeek = startOfWeek.plusWeeks(1).minusSeconds(1);

        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);

        List<MemberRankingDTO> dailyRanking = Arrays.asList(
                new MemberRankingDTO(3L, "user3", 300L, 0),
                new MemberRankingDTO(4L, "user4", 200L, 0),
                new MemberRankingDTO(1L, "user1", 100L, 0),
                new MemberRankingDTO(2L, "user2", 50L, 0),
                new MemberRankingDTO(5L, "user5", 0L, 0)
        );

        List<MemberRankingDTO> weeklyRanking = Arrays.asList(
                new MemberRankingDTO(1L, "user1", 100L, 0),
                new MemberRankingDTO(2L, "user2", 50L, 0),
                new MemberRankingDTO(3L, "user3", 300L, 0),
                new MemberRankingDTO(4L, "user4", 200L, 0),
                new MemberRankingDTO(5L, "user5", 0L, 0)
        );

        List<MemberRankingDTO> monthlyRanking = Arrays.asList(
                new MemberRankingDTO(3L, "user3", 300L, 0),
                new MemberRankingDTO(1L, "user1", 100L, 0),
                new MemberRankingDTO(4L, "user4", 200L, 0),
                new MemberRankingDTO(2L, "user2", 50L, 0),
                new MemberRankingDTO(5L, "user5", 0L, 0)
        );

        Mockito.when(pointHistoryRepository.findRankingByPeriod(startOfDay, endOfDay)).thenReturn(dailyRanking);
        Mockito.when(pointHistoryRepository.findRankingByPeriod(startOfWeek, endOfWeek)).thenReturn(weeklyRanking);
        Mockito.when(pointHistoryRepository.findRankingByPeriod(startOfMonth, endOfMonth)).thenReturn(monthlyRanking);

        // When
        CombinedRankingDTO combinedRanking = rankingService.getCombinedRanking();

        System.out.println("This is Combined Ranking: " + combinedRanking);

        // Then
        assertEquals(5, combinedRanking.getDailyRanking().size());
        assertEquals(5, combinedRanking.getWeeklyRanking().size());
        assertEquals(5, combinedRanking.getMonthlyRanking().size());

        // Daily Ranking Checks
        assertEquals(1, combinedRanking.getDailyRanking().get(0).getRank());
        assertEquals(2, combinedRanking.getDailyRanking().get(1).getRank());
        assertEquals(3, combinedRanking.getDailyRanking().get(2).getRank());
        assertEquals(4, combinedRanking.getDailyRanking().get(3).getRank());
        assertEquals(5, combinedRanking.getDailyRanking().get(4).getRank());

        // Weekly Ranking Checks
        assertEquals(1, combinedRanking.getWeeklyRanking().get(0).getRank());
        assertEquals(2, combinedRanking.getWeeklyRanking().get(1).getRank());
        assertEquals(3, combinedRanking.getWeeklyRanking().get(2).getRank());
        assertEquals(4, combinedRanking.getWeeklyRanking().get(3).getRank());
        assertEquals(5, combinedRanking.getWeeklyRanking().get(4).getRank());

        // Monthly Ranking Checks
        assertEquals(1, combinedRanking.getMonthlyRanking().get(0).getRank());
        assertEquals(2, combinedRanking.getMonthlyRanking().get(1).getRank());
        assertEquals(3, combinedRanking.getMonthlyRanking().get(2).getRank());
        assertEquals(4, combinedRanking.getMonthlyRanking().get(3).getRank());
        assertEquals(5, combinedRanking.getMonthlyRanking().get(4).getRank());
    }
}
