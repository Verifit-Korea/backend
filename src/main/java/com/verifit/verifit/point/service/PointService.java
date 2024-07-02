package com.verifit.verifit.point.service;

import com.verifit.verifit.global.exception.ApiException;
import com.verifit.verifit.global.exception.ExceptionCode;
import com.verifit.verifit.member.entity.Member;
import com.verifit.verifit.point.domain.entity.PointHistory;
import com.verifit.verifit.point.dto.MemberRankingDTO;
import com.verifit.verifit.point.repository.PointHistoryRepository;
import com.verifit.verifit.point.repository.PointRepository;
import com.verifit.verifit.point.domain.entity.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointService {

//    private final PointRepository pointRepository;
//
//    public long getMemberMonthlyAccumulatedPoint(long memberId, LocalDateTime now){
//        return pointRepository.findMemberMonthlyAccumulatedPoint(memberId, now);
//    }
//
//    @Transactional
//    public Point getMemberSeasonPoint(long memberId, LocalDateTime now){
//        return pointRepository.findMemberPointByDateTime(memberId, now)
//                .orElseGet(() -> pointRepository.save(Point.newSeason(memberId, now)));
//    }
//
//    public long getMemberAvailablePoint(long memberId, LocalDateTime now){
//        return pointRepository.findMemberAvailablePoint(memberId, now);
//    }

    private final PointHistoryRepository pointHistoryRepository;

    // 포인트 적립
    @Transactional
    public void addPoint(Member member, int points){
        PointHistory pointHistory = PointHistory.create(member, points);
        pointHistoryRepository.save(pointHistory);

        // Member 엔티티에 포인트 업데이트
        member.addAccumulatedPoint(points);
        member.addAvailablePoint(points);
    }

    // 포인트 사용
    @Transactional
    public void usePoint(Member member, int points){
        if (member.getAvailablePoint() < points){
            throw new ApiException(ExceptionCode.REQUEST_POINT_IS_NEGATIVE);
        }

        PointHistory pointHistory = PointHistory.create(member, -points);
        pointHistoryRepository.save(pointHistory);

        // Member 엔티티에 포인트 업데이트
        member.deductAvailablePoint(points);
    }

    // 특정 기간 동안의 멤버 랭킹 조회 -> PointHistoryRepository를 사용하여 지정된 기간 동안의 랭킹 조회
    public List<MemberRankingDTO> getRankingByPeriod(LocalDateTime start, LocalDateTime end){
        return pointHistoryRepository.findRankingByPeriod(start, end);
    }
}
