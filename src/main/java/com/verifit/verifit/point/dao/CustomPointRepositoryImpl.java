package com.verifit.verifit.point.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.verifit.verifit.point.domain.entity.Point;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.verifit.verifit.point.domain.entity.QPoint.point;

@RequiredArgsConstructor
public class CustomPointRepositoryImpl implements CustomPointRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public long findMemberMonthlyAccumulatedPoint(long memberId, LocalDateTime dateTime){
        Long accumulatedPoint = queryFactory.select(point.accumulatedPoint.sum())
                .from(point)
                .where(
                        point.member.id.eq(memberId),
                        point.startDateTime.year().eq(dateTime.getYear()),
                        point.startDateTime.month().eq(dateTime.getMonthValue()))
                .fetchOne();

        return accumulatedPoint == null ? 0 : accumulatedPoint;
    };

    @Override
    public Optional<Point> findMemberPointByDateTime(long memberId, LocalDateTime dateTime){
        Point findPoint = queryFactory.selectFrom(point)
                .where(
                        point.member.id.eq(memberId),
                        point.startDateTime.loe(dateTime),
                        point.endDateTime.gt(dateTime))
                .fetchOne();
        return Optional.ofNullable(findPoint);
    }

    @Override
    public long findMemberAvailablePoint(long memberId, LocalDateTime dateTime){
        Long availablePoint = queryFactory.select(point.availablePoint.sum())
                .from(point)
                .where(
                        point.member.id.eq(memberId),
                        point.startDateTime.loe(dateTime).and(point.endDateTime.gt(dateTime))
                                .or(point.startDateTime.loe(dateTime.minusWeeks(1L)).and(point.endDateTime.gt(dateTime.minusWeeks(1L)))))
                .fetchOne();

        return availablePoint == null ? 0 : availablePoint;
    }
}
