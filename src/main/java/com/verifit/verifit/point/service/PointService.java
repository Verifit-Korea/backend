package com.verifit.verifit.point.service;

import com.verifit.verifit.point.dao.PointRepository;
import com.verifit.verifit.point.domain.entity.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointService {

    private final PointRepository pointRepository;

    public long getMemberMonthlyAccumulatedPoint(long memberId, LocalDateTime now){
        return pointRepository.findMemberMonthlyAccumulatedPoint(memberId, now);
    }

    @Transactional
    public Point getMemberSeasonPoint(long memberId, LocalDateTime now){
        return pointRepository.findMemberPointByDateTime(memberId, now)
                .orElseGet(() -> pointRepository.save(Point.newSeason(memberId, now)));
    }

    public long getMemberAvailablePoint(long memberId, LocalDateTime now){
        return pointRepository.findMemberAvailablePoint(memberId, now);
    }
}
