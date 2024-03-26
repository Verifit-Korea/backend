package com.verifit.verifit.point.domain.entity;

import com.verifit.verifit.global.exception.ApiException;
import com.verifit.verifit.global.exception.ExceptionCode;
import com.verifit.verifit.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Member member;
    @PositiveOrZero(message = "누적 포인트는 0 이상이어야 합니다.")
    private long accumulatedPoint; // 누적 포인트
    @PositiveOrZero(message = "사용 가능한 포인트는 0 이상이어야 합니다.")
    private long availablePoint; // 사용 가능한 포인트
    private LocalDateTime startDateTime; // 시즌 시작일
    private LocalDateTime endDateTime; // 시즌 종료일

    public void use(long point){
        if (point <= 0) {
            throw new ApiException(ExceptionCode.REQUEST_POINT_IS_NEGATIVE);
        }
        this.availablePoint -= point;
    }

    public void accumulate(long point){
        if (point <= 0) {
            throw new ApiException(ExceptionCode.REQUEST_POINT_IS_NEGATIVE);
        }
        this.accumulatedPoint += point;
        this.availablePoint += point;
    }

    @Builder
    public Point(Member member, long accumulatedPoint, long availablePoint, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.member = member;
        this.accumulatedPoint = accumulatedPoint;
        this.availablePoint = availablePoint;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
