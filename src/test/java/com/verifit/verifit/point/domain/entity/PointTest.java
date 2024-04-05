package com.verifit.verifit.point.domain.entity;

import com.verifit.verifit.global.exception.ApiException;
import com.verifit.verifit.global.exception.ExceptionCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PointTest {
    
    @DisplayName("포인트를 적립한다.")
    @Test
    void accumulatePoint() {
        // given
        Point point = Point.builder()
                .availablePoint(1000)
                .accumulatedPoint(2000)
                .build();

        // when
        point.accumulate(1000);
    
        // then
        assertThat(point.getAvailablePoint()).isEqualTo(2000);
        assertThat(point.getAccumulatedPoint()).isEqualTo(3000);
    }

    @DisplayName("포인트를 사용한다.")
    @Test
    void usePoint() {
        // given
        Point point = Point.builder()
                .availablePoint(1000)
                .accumulatedPoint(2000)
                .build();

        // when
        point.use(1000);

        // then
        assertThat(point.getAvailablePoint()).isEqualTo(0);
        assertThat(point.getAccumulatedPoint()).isEqualTo(2000);
    }
    
    @DisplayName("사용하는 포인트는 항상 0보다 커야한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, -1000})
    void usePointZeroOrLess(long usePoint) {
        // given
        Point point = Point.builder()
                .availablePoint(1000)
                .accumulatedPoint(2000)
                .build();

        // when, then
        assertThatThrownBy(() -> point.use(usePoint))
                .isInstanceOf(ApiException.class)
                .hasMessage(ExceptionCode.REQUEST_POINT_IS_NEGATIVE.getMessage());
    }
    
    @DisplayName("적립하는 포인트는 항상 0보다 커야한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, -1000})
    void accumulatePointZeroOrLess(long accumulatePoint) {
        // given
        Point point = Point.builder()
                .availablePoint(1000)
                .accumulatedPoint(2000)
                .build();
        
        // when, then
        assertThatThrownBy(() -> point.accumulate(accumulatePoint))
                .isInstanceOf(ApiException.class)
                .hasMessage(ExceptionCode.REQUEST_POINT_IS_NEGATIVE.getMessage());
    }
    
    @DisplayName("새로운 시즌 포인트를 생성한다.")
    @Test
    void newSeason() {
        // given
        LocalDateTime now = LocalDateTime.of(2024, 4, 4, 10, 10, 10);

        // when
        Point point = Point.newSeason(1L, now);

        // then
        assertThat(point.getMember().getId()).isEqualTo(1L);
        assertThat(point.getAccumulatedPoint()).isEqualTo(0);
        assertThat(point.getAvailablePoint()).isEqualTo(0);
        assertThat(point.getStartDateTime()).isEqualTo("2024-04-01T00:00:00");
        assertThat(point.getEndDateTime()).isEqualTo("2024-04-07T23:59:59");
    }
}