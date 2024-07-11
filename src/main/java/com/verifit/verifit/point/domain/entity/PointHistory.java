package com.verifit.verifit.point.domain.entity;

import com.verifit.verifit.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false) // 외래키
    private Member member;

    @Column(nullable = false)
    private int points;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public static PointHistory create(Member member, int points) {
        return PointHistory.builder()
                .member(member)
                .points(points)
                .createdAt(LocalDateTime.now())
                .build();
    }

}
