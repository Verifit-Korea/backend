package com.verifit.verifit.point.repository;

import com.verifit.verifit.member.entity.Member;
import com.verifit.verifit.point.domain.entity.PointHistory;
import com.verifit.verifit.point.dto.MemberRankingDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

// createdAt 컬럼에 인덱스 추가하면 성능 향상 가능할 거 같음.
public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    @Query("SELECT new com.verifit.verifit.point.dto.MemberRankingDTO(ph.member.id, ph.member.nickname, SUM(ph.points), 0) " +
            "FROM PointHistory ph " +
            "WHERE ph.createdAt BETWEEN :start AND :end " +
            "GROUP BY ph.member.id, ph.member.nickname " +
            "ORDER BY SUM(ph.points) DESC")
    List<MemberRankingDTO> findRankingByPeriod(LocalDateTime start, LocalDateTime end);
}
