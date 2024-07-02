package com.verifit.verifit.point.service;

import com.verifit.verifit.point.dto.CombinedRankingDTO;
import com.verifit.verifit.point.dto.MemberRankingDTO;
import com.verifit.verifit.point.repository.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

// 전체 랭킹은 자주 바뀌지 않으니 캐싱을 고려해볼만 함.
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankingService {
    private final PointHistoryRepository pointHistoryRepository;

    // 일간 랭킹 조회
    public List<MemberRankingDTO> getDailyRanking() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        List<MemberRankingDTO> rawRanking = pointHistoryRepository.findRankingByPeriod(startOfDay, endOfDay);
        return assignRank(rawRanking);
    }

    // 주간 랭킹 조회
    public List<MemberRankingDTO> getWeeklyRanking() {
        LocalDateTime startOfWeek = LocalDate.now().with(java.time.DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime endOfWeek = startOfWeek.plusWeeks(1).minusSeconds(1);

        List<MemberRankingDTO> rawRanking = pointHistoryRepository.findRankingByPeriod(startOfWeek, endOfWeek);
        return assignRank(rawRanking);
    }

    // 월간 랭킹 조회
    public List<MemberRankingDTO> getMonthlyRanking() {
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);

        List<MemberRankingDTO> rawRanking = pointHistoryRepository.findRankingByPeriod(startOfMonth, endOfMonth);
        return assignRank(rawRanking);
    }

    // 전체 랭킹 조회
    public CombinedRankingDTO getCombinedRanking() {
        List<MemberRankingDTO> dailyRanking = getDailyRanking();
        List<MemberRankingDTO> weeklyRanking = getWeeklyRanking();
        List<MemberRankingDTO> monthlyRanking = getMonthlyRanking();

        return new CombinedRankingDTO(dailyRanking, weeklyRanking, monthlyRanking);
    }


    // 랭킹 부여
    private List<MemberRankingDTO> assignRank(List<MemberRankingDTO> rawRanking){
        AtomicInteger rank = new AtomicInteger(1); // AtomicInteger로 랭킹을 순차적으로 부여
        return rawRanking.stream()
                .sorted((r1, r2) -> Long.compare(r2.getTotalPoints(), r1.getTotalPoints())) // 포인트 내림차순 정렬
                .map(ranking -> {
                    int currentRank = rank.getAndIncrement();
                    return new MemberRankingDTO(ranking.getMember(), ranking.getTotalPoints(), currentRank);
                })
                .collect(Collectors.toList());
    }

}
