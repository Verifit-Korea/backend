package com.verifit.verifit.point.service;

import com.verifit.verifit.member.entity.Member;
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

        return getRankingByPeriod(startOfDay, endOfDay);
    }

    // 주간 랭킹 조회
    public List<MemberRankingDTO> getWeeklyRanking() {
        LocalDateTime startOfWeek = LocalDate.now().with(java.time.DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime endOfWeek = startOfWeek.plusWeeks(1).minusSeconds(1);

        return getRankingByPeriod(startOfWeek, endOfWeek);
    }

    // 월간 랭킹 조회
    public List<MemberRankingDTO> getMonthlyRanking() {
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);

        return getRankingByPeriod(startOfMonth, endOfMonth);
    }

    // 전체 랭킹 조회
    public CombinedRankingDTO getCombinedRanking() {
        return new CombinedRankingDTO(getDailyRanking(), getWeeklyRanking(), getMonthlyRanking());
    }

    private List<MemberRankingDTO> getRankingByPeriod(LocalDateTime start, LocalDateTime end) {
        List<MemberRankingDTO> rawRanking = pointHistoryRepository.findRankingByPeriod(start, end);
        return assignRankWithTieHandling(rawRanking);
    }


    // 랭킹 부여 (동점자 처리 포함)
    private List<MemberRankingDTO> assignRankWithTieHandling(List<MemberRankingDTO> rawRanking) {
        if (rawRanking.isEmpty()) {
            return rawRanking;
        }

        rawRanking.sort((r1, r2) -> Long.compare(r2.getTotalPoints(), r1.getTotalPoints())); // 내림차순 정렬

        int currentRank = 1;
        int countSameRank = 0;
        long previousPoints = rawRanking.get(0).getTotalPoints();

        for (MemberRankingDTO currentMember : rawRanking) {
            if (currentMember.getTotalPoints() < previousPoints) {
                currentRank += countSameRank;
                countSameRank = 1;
                previousPoints = currentMember.getTotalPoints();
            } else {
                countSameRank++;
            }

            currentMember.setRank(currentRank);
        }

        return rawRanking;
    }

}
