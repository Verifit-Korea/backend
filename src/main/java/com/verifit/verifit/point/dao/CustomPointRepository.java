package com.verifit.verifit.point.dao;

import com.verifit.verifit.point.domain.entity.Point;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CustomPointRepository {

    public long findMemberMonthlyAccumulatedPoint(long memberId, LocalDateTime dateTime);
    public Optional<Point> findMemberPointByDateTime(long memberId, LocalDateTime dateTime);
    public long findMemberAvailablePoint(long memberId, LocalDateTime dateTime);
}
