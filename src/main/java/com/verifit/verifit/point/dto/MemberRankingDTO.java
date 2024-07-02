package com.verifit.verifit.point.dto;

import com.verifit.verifit.member.entity.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberRankingDTO {
    private Member member;
    private Long totalPoints;
    private int rank;

    public MemberRankingDTO(Member member, Long totalPoints, int rank) {
        this.member = member;
        this.totalPoints = totalPoints;
        this.rank = rank;
    }
}
